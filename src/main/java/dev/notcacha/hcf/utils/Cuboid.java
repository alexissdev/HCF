package dev.notcacha.hcf.utils;

import org.bukkit.Location;

public class Cuboid {

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public Cuboid(Location one, Location two) {
        this.minX = Math.min(one.getX(), two.getX());
        this.maxX = Math.max(one.getX(), two.getX());

        this.minY = Math.min(one.getY(), two.getY());
        this.maxY = Math.max(one.getY(), two.getY());

        this.minZ = Math.min(one.getZ(), two.getZ());
        this.maxZ = Math.max(one.getZ(), two.getZ());
    }

    /**
     * @return min X from cuboid
     */

    public double getMinX() {
        return minX;
    }

    /**
     * @return max X from cuboid
     */

    public double getMaxX() {
        return maxX;
    }

    /**
     * @return min Y from cuboid
     */

    public double getMinY() {
        return minY;
    }

    /**
     * @return max Y from cuboid
     */

    public double getMaxY() {
        return maxY;
    }

    /**
     * @return min Z from cuboid
     */

    public double getMinZ() {
        return minZ;
    }

    /**
     * @return max Z from cuboid
     */

    public double getMaxZ() {
        return maxZ;
    }

    /**
     * @return if the {@param playerLocation} match the coordinates of the cuboid
     */

    public boolean has(Location playerLocation) {
        return playerLocation.getX() > getMinX() && playerLocation.getX() <= getMaxX() + 1
                && playerLocation.getZ() > getMinZ() && playerLocation.getZ() <= getMaxZ() + 1;
    }

    /**
     * @param detectY detect y or else
     * @return if the {@param playerLocation} match the coordinates of the cuboid
     */

    public boolean has(Location playerLocation, boolean detectY) {
        if (detectY) {
            return playerLocation.getX() > getMinX() && playerLocation.getX() <= getMaxX() + 1
                    && playerLocation.getY() > getMinY() && playerLocation.getY() <= getMaxY() + 1
                    && playerLocation.getZ() > getMinZ() && playerLocation.getZ() <= getMaxZ() + 1;
        }
        return has(playerLocation);
    }
}
