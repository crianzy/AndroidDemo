/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v71.widget;

import java.util.List;

/**
 * 队列操作对象 整理对象
 */
class OpReorderer {

    final Callback mCallback;

    public OpReorderer(Callback callback) {
        mCallback = callback;
    }

    /**
     * 整理 Ops
     * @param ops
     */
    void reorderOps(List<AdapterHelper.UpdateOp> ops) {
        // since move operations breaks continuity, their effects on ADD/RM are hard to handle.
        // we push them to the end of the list so that they can be handled easily.
        int badMove;
        while ((badMove = getLastMoveOutOfOrder(ops)) != -1) {
            swapMoveOp(ops, badMove, badMove + 1);
        }
    }

    /**
     * 交换 移动 操作
     * @param list
     * @param badMove  move 操作的位置
     * @param next
     */
    private void swapMoveOp(List<AdapterHelper.UpdateOp> list, int badMove, int next) {
        final AdapterHelper.UpdateOp moveOp = list.get(badMove);// 上一次的move 操作
        final AdapterHelper.UpdateOp nextOp = list.get(next); // 下一个操作
        switch (nextOp.cmd) {
            case AdapterHelper.UpdateOp.REMOVE:
                // 执行remove 操作
                swapMoveRemove(list, badMove, moveOp, next, nextOp);
                break;
            case AdapterHelper.UpdateOp.ADD:
                // 执行添加操作
                swapMoveAdd(list, badMove, moveOp, next, nextOp);
                break;
            case AdapterHelper.UpdateOp.UPDATE:
                // 执行 更新操作
                swapMoveUpdate(list, badMove, moveOp, next, nextOp);
                break;
        }
    }

    /**
     *
     * remove 操作
     * @param list
     * @param movePos move操作的位置
     * @param moveOp  move 操作对象
     * @param removePos remove 操作的位置
     * @param removeOp remove 操作的对象
     */
    void swapMoveRemove(List<AdapterHelper.UpdateOp> list, int movePos, AdapterHelper.UpdateOp moveOp,
                        int removePos, AdapterHelper.UpdateOp removeOp) {
        AdapterHelper.UpdateOp extraRm = null;
        // check if move is nulled out by remove
        boolean revertedMove = false;

        final boolean moveIsBackwards;

        if (moveOp.positionStart < moveOp.itemCount) {
            // 如果移动操作的开始位置 < itemCount
            moveIsBackwards = false;
            // 表示 move 是向前
            if (removeOp.positionStart == moveOp.positionStart
                    && removeOp.itemCount == moveOp.itemCount - moveOp.positionStart) {
                revertedMove = true;
            }
        } else {
            // 表示 move 是向后
            moveIsBackwards = true;
            if (removeOp.positionStart == moveOp.itemCount + 1 &&
                    removeOp.itemCount == moveOp.positionStart - moveOp.itemCount) {
                revertedMove = true;
            }
        }

        // going in reverse, first revert the effect of add
        if (moveOp.itemCount < removeOp.positionStart) {
            removeOp.positionStart--;
        } else if (moveOp.itemCount < removeOp.positionStart + removeOp.itemCount) {
            // move is removed.
            removeOp.itemCount --;
            moveOp.cmd = AdapterHelper.UpdateOp.REMOVE;
            moveOp.itemCount = 1;
            if (removeOp.itemCount == 0) {
                list.remove(removePos);
                mCallback.recycleUpdateOp(removeOp);
            }
            // no need to swap, it is already a remove
            return;
        }

        // now affect of add is consumed. now apply effect of first remove
        if (moveOp.positionStart <= removeOp.positionStart) {
            removeOp.positionStart++;
        } else if (moveOp.positionStart < removeOp.positionStart + removeOp.itemCount) {
            final int remaining = removeOp.positionStart + removeOp.itemCount
                    - moveOp.positionStart;
            extraRm = mCallback.obtainUpdateOp(AdapterHelper.UpdateOp.REMOVE, moveOp.positionStart + 1, remaining, null);
            removeOp.itemCount = moveOp.positionStart - removeOp.positionStart;
        }

        // if effects of move is reverted by remove, we are done.
        if (revertedMove) {
            list.set(movePos, removeOp);
            list.remove(removePos);
            mCallback.recycleUpdateOp(moveOp);
            return;
        }

        // now find out the new locations for move actions
        if (moveIsBackwards) {
            if (extraRm != null) {
                if (moveOp.positionStart > extraRm.positionStart) {
                    moveOp.positionStart -= extraRm.itemCount;
                }
                if (moveOp.itemCount > extraRm.positionStart) {
                    moveOp.itemCount -= extraRm.itemCount;
                }
            }
            if (moveOp.positionStart > removeOp.positionStart) {
                moveOp.positionStart -= removeOp.itemCount;
            }
            if (moveOp.itemCount > removeOp.positionStart) {
                moveOp.itemCount -= removeOp.itemCount;
            }
        } else {
            if (extraRm != null) {
                if (moveOp.positionStart >= extraRm.positionStart) {
                    moveOp.positionStart -= extraRm.itemCount;
                }
                if (moveOp.itemCount >= extraRm.positionStart) {
                    moveOp.itemCount -= extraRm.itemCount;
                }
            }
            if (moveOp.positionStart >= removeOp.positionStart) {
                moveOp.positionStart -= removeOp.itemCount;
            }
            if (moveOp.itemCount >= removeOp.positionStart) {
                moveOp.itemCount -= removeOp.itemCount;
            }
        }

        list.set(movePos, removeOp);
        if (moveOp.positionStart != moveOp.itemCount) {
            list.set(removePos, moveOp);
        } else {
            list.remove(removePos);
        }
        if (extraRm != null) {
            list.add(movePos, extraRm);
        }
    }

    private void swapMoveAdd(List<AdapterHelper.UpdateOp> list, int move, AdapterHelper.UpdateOp moveOp, int add,
                             AdapterHelper.UpdateOp addOp) {
        int offset = 0;
        // going in reverse, first revert the effect of add
        if (moveOp.itemCount < addOp.positionStart) {
            offset--;
        }
        if (moveOp.positionStart < addOp.positionStart) {
            offset++;
        }
        if (addOp.positionStart <= moveOp.positionStart) {
            moveOp.positionStart += addOp.itemCount;
        }
        if (addOp.positionStart <= moveOp.itemCount) {
            moveOp.itemCount += addOp.itemCount;
        }
        addOp.positionStart += offset;
        list.set(move, addOp);
        list.set(add, moveOp);
    }

    void swapMoveUpdate(List<AdapterHelper.UpdateOp> list, int move, AdapterHelper.UpdateOp moveOp, int update,
                        AdapterHelper.UpdateOp updateOp) {
        AdapterHelper.UpdateOp extraUp1 = null;
        AdapterHelper.UpdateOp extraUp2 = null;
        // going in reverse, first revert the effect of add
        if (moveOp.itemCount < updateOp.positionStart) {
            updateOp.positionStart--;
        } else if (moveOp.itemCount < updateOp.positionStart + updateOp.itemCount) {
            // moved item is updated. add an update for it
            updateOp.itemCount--;
            extraUp1 = mCallback.obtainUpdateOp(AdapterHelper.UpdateOp.UPDATE, moveOp.positionStart, 1, updateOp.payload);
        }
        // now affect of add is consumed. now apply effect of first remove
        if (moveOp.positionStart <= updateOp.positionStart) {
            updateOp.positionStart++;
        } else if (moveOp.positionStart < updateOp.positionStart + updateOp.itemCount) {
            final int remaining = updateOp.positionStart + updateOp.itemCount
                    - moveOp.positionStart;
            extraUp2 = mCallback.obtainUpdateOp(AdapterHelper.UpdateOp.UPDATE, moveOp.positionStart + 1, remaining,
                    updateOp.payload);
            updateOp.itemCount -= remaining;
        }
        list.set(update, moveOp);
        if (updateOp.itemCount > 0) {
            list.set(move, updateOp);
        } else {
            list.remove(move);
            mCallback.recycleUpdateOp(updateOp);
        }
        if (extraUp1 != null) {
            list.add(move, extraUp1);
        }
        if (extraUp2 != null) {
            list.add(move, extraUp2);
        }
    }

    /**
     * 获取上次 移动的 操作的顺序
     * @param list
     * @return
     */
    private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> list) {
        boolean foundNonMove = false;
        for (int i = list.size() - 1; i >= 0; i--) {
            final AdapterHelper.UpdateOp op1 = list.get(i);
            if (op1.cmd == AdapterHelper.UpdateOp.MOVE) {
                // 反向 遍历寻找 第一个 Move 操作 返回索引
                if (foundNonMove) {
                    return i;
                }
            } else {
                foundNonMove = true;
            }
        }
        return -1;
    }

    static interface Callback {


        /**
         * 获取 AdapterHelper UpdateOp 队列操作
         * @param cmd
         * @param startPosition
         * @param itemCount
         * @param payload
         * @return
         */
        AdapterHelper.UpdateOp obtainUpdateOp(int cmd, int startPosition, int itemCount, Object payload);

        void recycleUpdateOp(AdapterHelper.UpdateOp op);
    }
}
