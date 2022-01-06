package pepse.world.movement;

/**
 * Used in order to control the avatar energy
 *
 * @author Tomer Goldberg
 */
public class AvatarEnergyHandler {
    private float energyLevel;
    private final int maxLevel;
    private static final float CHANGE = 0.5f;

    private boolean isRecharging = false;

    /**
     * creator for AvatarEnergyHandler
     *
     * @param initalLevel inital value for energy change
     */
    public AvatarEnergyHandler(int initalLevel) {
        this.energyLevel = initalLevel;
        this.maxLevel = initalLevel;
    }

    /**
     * gets the current energy lvl
     *
     * @return 0 if the object is recharging, energy level otherwise
     */
    public float getCurrentLevel() {
        if (this.isRecharging)
            return 0;
        return this.energyLevel;
    }

    /**
     * increase the energy level
     */
    public void increaseLevel() {
        if (this.energyLevel + CHANGE >= this.maxLevel) {
            this.energyLevel = this.maxLevel;
            this.isRecharging = false;
        } else {
            this.energyLevel += CHANGE;
        }
    }

    /**
     * decrease the energy level
     */
    public void decreaseLevel() {
        if (this.energyLevel - CHANGE <= 0) {
            this.energyLevel = 0;
            this.isRecharging = true;
        } else {
            this.energyLevel -= CHANGE;
        }
    }
}
