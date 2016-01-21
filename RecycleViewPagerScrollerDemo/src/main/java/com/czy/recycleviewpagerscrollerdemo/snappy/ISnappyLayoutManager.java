package com.czy.recycleviewpagerscrollerdemo.snappy;

/**
 * Created by chenzhiyong on 16/1/20.
 */
public interface ISnappyLayoutManager {

    /**
     * @param velocityX
     * @param velocityY
     * @return the resultant position from a fling of the given velocity.
     */
    int getPositionForVelocity(int velocityX, int velocityY);

    int getNextFixedScrollPos(int velocityX, int velocityY);

    /**
     * @return the position this list must scroll to to fix a state where the
     * views are not snapped to grid.
     */
    int getFixScrollPos();
}
