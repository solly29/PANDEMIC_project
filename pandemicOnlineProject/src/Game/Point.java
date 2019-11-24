package Game;
//좌표다룰때 쉽게하려고 만든 Point 클래스
public class Point {
	int x,y;
	Point (int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String toString() {
		return String.format("%d %d",x,y);
	}
}
