package com.accbdd.reclamation_util.mixins;

import com.accbdd.reclamation_util.ReclamationUtil;
import com.google.gson.JsonObject;
import com.klikli_dev.theurgy.content.recipe.ingredient.FluidIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidIngredient.class)
public abstract class FluidIngredientNbtPatch {
    @Shadow
    public static Fluid fluidFromJson(JsonObject pItemObject) {
        return null;
    }

    @Inject(
            method = "fluidValueFromJson",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void parseOptionalNbt(JsonObject pJson, CallbackInfoReturnable<FluidIngredient.Value> cir) {
        if (pJson.has("fluid")) {
            Fluid fluid = FluidIngredient.fluidFromJson(pJson);
            FluidStack stack = new FluidStack(fluid, 1);
            if (pJson.has("nbt")) {
                try {
                    CompoundTag tag;
                    com.google.gson.JsonElement nbtElement = pJson.get("nbt");

                    if (nbtElement.isJsonPrimitive() && nbtElement.getAsJsonPrimitive().isString()) {
                        tag = net.minecraft.nbt.TagParser.parseTag(nbtElement.getAsString());
                    } else {
                        tag = CompoundTag.CODEC.parse(com.mojang.serialization.JsonOps.INSTANCE, nbtElement)
                                .getOrThrow(false, s -> {});

                        if (tag.contains("Purity")) {
                            tag.putInt("Purity", tag.getInt("Purity"));
                        }
                    }

                    stack.setTag(tag);
                } catch (Exception e) {
                    ReclamationUtil.LOGGER.debug("failed to parse nbt in theurgy fluid ingredient", e);
                }
            }

            cir.setReturnValue(new FluidIngredient.FluidValue(stack));
        }
    }
}
