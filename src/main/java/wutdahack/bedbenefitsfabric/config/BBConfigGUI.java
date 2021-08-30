package wutdahack.bedbenefitsfabric.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import wutdahack.bedbenefitsfabric.BedBenefitsFabric;

public class BBConfigGUI {

    BBConfig config = BedBenefitsFabric.getInstance().config;

    public Screen getConfigScreen(Screen parent, boolean isTransparent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableText("text.config.bedbenefitsfabric.title"));
        builder.setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/dirt.png"));
        builder.setSavingRunnable(() -> BedBenefitsFabric.getInstance().saveConfig());
        ConfigCategory general = builder.getOrCreateCategory(Text.of("general"));
        ConfigEntryBuilder configEntryBuilder = builder.entryBuilder();

        general.addEntry(
                configEntryBuilder
                        .startBooleanToggle(new TranslatableText("text.config.bedbenefitsfabric.option.restoreHealth"), config.restoreHealth)
                        .setDefaultValue(true)
                        .setTooltip(Text.of("enable restoring of health\nwhen sleeping. default = true"))
                        .setSaveConsumer(newValue -> config.setRestoreHealth(newValue))
                        .build()
        );

        general.addEntry(
                configEntryBuilder
                        .startBooleanToggle(new TranslatableText("text.config.bedbenefitsfabric.option.restoreFullHealth"), config.restoreFullHealth)
                        .setDefaultValue(false)
                        .setTooltip(Text.of("restore full health instead of\nthe health amount below. default = false"))
                        .setSaveConsumer(newValue -> config.setRestoreFullHealth(newValue))
                        .build()
        );

        general.addEntry(
                configEntryBuilder
                        .startFloatField(new TranslatableText("text.config.bedbenefitsfabric.option.healAmount"), config.healAmount)
                        .setDefaultValue(10F)
                        .setTooltip(Text.of("the amount of health\nto be restored. default = 10.0"))
                        .setSaveConsumer(newValue -> config.setHealAmount(newValue))
                        .build()
        );

        general.addEntry(
                configEntryBuilder
                        .startBooleanToggle(new TranslatableText("text.config.bedbenefitsfabric.option.removeBadEffects"), config.removeBadEffects)
                        .setDefaultValue(true)
                        .setTooltip(Text.of("enable the removal of bad\neffects when sleeping. default = true"))
                        .setSaveConsumer(newValue -> config.setRemoveBadEffects(newValue))
                        .build()
        );

        general.addEntry(
                configEntryBuilder
                        .startBooleanToggle(new TranslatableText("text.config.bedbenefitsfabric.option.removePositiveEffects"), config.removePositiveEffects)
                        .setDefaultValue(true)
                        .setTooltip(Text.of("enable the removal of positive\neffects when sleeping. default = true"))
                        .setSaveConsumer(newValue -> config.setRemovePositiveEffects(newValue))
                        .build()
        );

        return builder.setTransparentBackground(isTransparent).build();
    }
}
