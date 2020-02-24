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

    public static final double OFFSET_X = 0.5D;
    public static final double OFFSET_Y = 0.22D;
    public static final double OFFSET_Z = 0.5D;

    public DummyEntityForSitting(World world) {
        this(Registration.DUMMY_ENTITY_TYPE, world);
    }

    public DummyEntityForSitting(EntityType<? extends DummyEntityForSitting> type, World world) {
        super(type, world);
    }

    public DummyEntityForSitting(World world, BlockPos pos) {
        super(Registration.DUMMY_ENTITY_TYPE, world);
        double x = pos.getX() + OFFSET_X;
        double y = pos.getY() + OFFSET_Y;
        double z = pos.getZ() + OFFSET_Z;
        setPos(x, y, z);
        updateTrackedPosition(x, y, z);
        updatePosition(x, y, z);
        noClip = true;
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
