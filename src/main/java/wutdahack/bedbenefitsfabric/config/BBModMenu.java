package wutdahack.bedbenefitsfabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;

public class BBModMenu implements ModMenuApi {

    BBConfigGUI configGUI = new BBConfigGUI();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> configGUI.getConfigScreen(parent, MinecraftClient.getInstance().world != null);
    }

}
