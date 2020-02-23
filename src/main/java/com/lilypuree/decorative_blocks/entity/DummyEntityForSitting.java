package com.lilypuree.decorative_blocks.entity;

import com.lilypuree.decorative_blocks.setup.Registration;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DummyEntityForSitting extends Entity {

    protected boolean shouldKill = false;

    public DummyEntityForSitting(EntityType<? extends DummyEntityForSitting> type, World world) {
        super(type, world);
    }

    public DummyEntityForSitting(World world, BlockPos pos) {
        super(Registration.DUMMY_ENTITY_TYPE, world);
        setPos(pos.getX() + 0.5D, pos.getY() + 0.22D, pos.getZ() + 0.5D);
        noClip = true;
    }


    // TODO: remove this method that runs each tick, replace by event listener
    @Override
    public void tick() {
        if (!world.isClient) {
            if (!hasPlayerRider() && shouldKill) {
                this.kill();
            } else shouldKill = !hasPlayerRider();
        }
    }


    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        //super.readCustomDataFromTag(tag);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        //super.writeCustomDataToTag(tag);
    }


    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        this.writeToPacket(buf);
        return ServerSidePacketRegistry.INSTANCE.toPacket(Registration.SPAWN_DUMMY_SEAT_ID, buf);
    }

    public void writeToPacket(PacketByteBuf buf) {
        buf.writeVarInt(this.getEntityId());
        buf.writeUuid(this.uuid);
        buf.writeDouble(this.getX());
        buf.writeDouble(this.getY());
        buf.writeDouble(this.getZ());
    }

    public void readFromPacket(PacketByteBuf buf) {
        this.setEntityId(buf.readVarInt());
        this.setUuid(buf.readUuid());
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        this.updateTrackedPosition(x, y, z);
        this.updatePosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }
}
