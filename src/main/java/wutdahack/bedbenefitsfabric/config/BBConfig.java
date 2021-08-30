package wutdahack.bedbenefitsfabric.config;

public class BBConfig {

    public boolean restoreHealth = true;
    public boolean restoreFullHealth = false;
    public float healAmount = 10F;
    public boolean removeBadEffects = true;
    public boolean removePositiveEffects = true;


    public boolean shouldRestoreHealth() {
        return this.restoreHealth;
    }

    public boolean shouldRestoreFullHealth() {
        return this.restoreFullHealth;
    }

    public float getHealAmount() {
        return this.healAmount;
    }

    public boolean shouldClearBadEffects() {
        return this.removeBadEffects;
    }

    public boolean shouldClearGoodEffects() {
        return this.removePositiveEffects;
    }

    public void setRestoreHealth(boolean restoreHealth) {
        this.restoreHealth = restoreHealth;
    }

    public void setRestoreFullHealth(boolean restoreFullHealth) {
        this.restoreFullHealth = restoreFullHealth;
    }

    public void setHealAmount(float healAmount) {
        this.healAmount = healAmount;
    }


    public void setRemoveBadEffects(boolean removeBadEffects) {
        this.removeBadEffects = removeBadEffects;
    }

    public void setRemovePositiveEffects(boolean removePositiveEffects) {
        this.removePositiveEffects = removePositiveEffects;
    }
}
