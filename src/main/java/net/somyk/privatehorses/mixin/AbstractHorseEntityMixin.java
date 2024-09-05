package net.somyk.privatehorses.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.somyk.privatehorses.util.Utilities.canInteract;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity implements Tameable {

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void checkInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        boolean canInteract = canInteract((AbstractHorseEntity) (Object) this, player);
        if(!canInteract) cir.setReturnValue(ActionResult.FAIL);
    }

    @Inject(method = "beforeLeashTick", at = @At("HEAD"))
    public void detachLeash(Entity leashHolder, float distance, CallbackInfoReturnable<Boolean> cir) {
        AbstractHorseEntity horse = (AbstractHorseEntity) (Object) this;
        if(!canInteract(horse, leashHolder)) {
            horse.detachLeash(true, false);
            if(leashHolder instanceof PlayerEntity player) player.giveItemStack(Items.LEAD.getDefaultStack());
        }
    }

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}