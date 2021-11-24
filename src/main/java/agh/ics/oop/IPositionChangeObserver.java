package agh.ics.oop;

public interface IPositionChangeObserver extends IWorldMap {
     void positionChanged (Vector2d oldPosition, Vector2d newPosition);
}
