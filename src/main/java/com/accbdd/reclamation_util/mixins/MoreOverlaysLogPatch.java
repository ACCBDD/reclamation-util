package com.accbdd.reclamation_util.mixins;

import at.ridgo8.moreoverlays.itemsearch.JeiModule;
import at.ridgo8.moreoverlays.util.ReflectionUtil;
import mezz.jei.api.runtime.IIngredientListOverlay;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(JeiModule.class)
public class MoreOverlaysLogPatch {
    @Shadow(remap = false)
    public static IIngredientListOverlay overlay;
    @Shadow(remap = false)
    private static EditBox textField;

    /**
     * @author ACCBDD
     * @reason stop console spam when JEI is present but not used (e.g. EMI on top of JEI)
     */
    @Overwrite(remap = false)
    public static void updateModule() {
        if (overlay != null) {
            textField = ReflectionUtil.findFieldsWithClass(overlay, EditBox.class)
                    .findFirst()
                    .orElse(null);
        } else {
            textField = null;
        }
    }
}
