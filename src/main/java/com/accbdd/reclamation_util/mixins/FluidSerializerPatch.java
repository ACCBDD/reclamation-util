package com.accbdd.reclamation_util.mixins;

import com.accbdd.reclamation_util.ReclamationUtil;
import com.google.gson.JsonObject;
import com.klikli_dev.theurgy.content.recipe.ingredient.FluidIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidIngredient.FluidValue.class)
public class FluidSerializerPatch {
    @Shadow
    @Final
    private FluidStack fluid;

    @Inject(method = "serialize()Lcom/google/gson/JsonObject;", at = @At("RETURN"), remap = false)
    private void serializeOptionalNbt(CallbackInfoReturnable<JsonObject> cir) {
        JsonObject json = cir.getReturnValue();

        if (this.fluid.hasTag() && !this.fluid.getTag().isEmpty()) {
            try {
                com.google.gson.JsonElement nbtJson = CompoundTag.CODEC.encodeStart(com.mojang.serialization.JsonOps.INSTANCE, this.fluid.getTag())
                        .getOrThrow(false, s -> {});

                json.add("nbt", nbtJson);
            } catch (Exception e) {
                ReclamationUtil.LOGGER.debug("failed to serialize nbt in theurgy fluid ingredient", e);
            }
        }
    }
}
