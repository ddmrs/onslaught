package onslaught.model;

public interface TurretBehaviour {
	public void turn(int degrees);
	public void shoot();
	public void upgradeRate();
	public void upgradeRange();
	public void upgradeDamage();
	public void moveTo(int x, int y);
}
