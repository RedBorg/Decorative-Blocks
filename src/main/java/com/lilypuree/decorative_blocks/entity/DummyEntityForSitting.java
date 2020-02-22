package com.lilypuree.decorative_blocks.entity;

import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DummyEntityForSitting extends Entity {

    public DummyEntityForSitting(EntityType<? extends DummyEntityForSitting> type, World world) {
        super(type, world);
    }

    public DummyEntityForSitting(World world, BlockPos pos) {
        super(Registration.DUMMY_ENTITY_TYPE, world);
        setPos(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        noClip = true;
    }


    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
    }


    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
