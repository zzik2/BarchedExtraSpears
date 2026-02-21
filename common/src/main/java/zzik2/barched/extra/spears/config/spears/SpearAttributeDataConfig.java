package zzik2.barched.extra.spears.config.spears;


import zzik2.barched.extra.spears.data.items.SpearAttributeData;

public class SpearAttributeDataConfig {

    private float swingSeconds;
    private float kineticDamageMultiplier;
    private float delaySeconds;
    private float damageCondDurationSeconds;
    private float damageCondMinSpeed;
    private float knockbackCondDurationSeconds;
    private float knockbackCondMinSpeed;
    private float dismountCondDurationSeconds;
    private float dismountCondMinRelativeSpeed;

    public SpearAttributeDataConfig() {
    }

    public static SpearAttributeDataConfig fromSpearAttributeData(SpearAttributeData SpearAttributeData) {
        SpearAttributeDataConfig config = new SpearAttributeDataConfig();
        config.swingSeconds = SpearAttributeData.swingSeconds();
        config.kineticDamageMultiplier = SpearAttributeData.kineticDamageMultiplier();
        config.delaySeconds = SpearAttributeData.delaySeconds();
        config.damageCondDurationSeconds = SpearAttributeData.damageCondDurationSeconds();
        config.damageCondMinSpeed = SpearAttributeData.damageCondMinSpeed();
        config.knockbackCondDurationSeconds = SpearAttributeData.knockbackCondDurationSeconds();
        config.knockbackCondMinSpeed = SpearAttributeData.knockbackCondMinSpeed();
        config.dismountCondDurationSeconds = SpearAttributeData.dismountCondDurationSeconds();
        config.dismountCondMinRelativeSpeed = SpearAttributeData.dismountCondMinRelativeSpeed();
        return config;
    }

    public SpearAttributeData toSpearAttributeData() {
        return new SpearAttributeData(
                swingSeconds,
                kineticDamageMultiplier,
                delaySeconds,
                damageCondDurationSeconds,
                damageCondMinSpeed,
                knockbackCondDurationSeconds,
                knockbackCondMinSpeed,
                dismountCondDurationSeconds,
                dismountCondMinRelativeSpeed);
    }
}
