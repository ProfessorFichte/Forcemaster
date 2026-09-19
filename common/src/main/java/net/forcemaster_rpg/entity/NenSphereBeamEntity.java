package net.forcemaster_rpg.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NenSphereBeamEntity extends Entity {
    public static EntityType<NenSphereBeamEntity> ENTITY_TYPE;
    public static final int LIFESPAN_TICKS = 10;

    private static final TrackedData<Float> WIDTH =
            DataTracker.registerData(NenSphereBeamEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> LENGTH =
            DataTracker.registerData(NenSphereBeamEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public NenSphereBeamEntity(EntityType<? extends NenSphereBeamEntity> type, World world) {
        super(type, world);
        this.noClip = true;
        this.setNoGravity(true);
        // Beam mesh renders past the entity's own position, so origin-only frustum culling would hide it
        this.ignoreCameraFrustum = true;
    }

    public NenSphereBeamEntity(World world, Vec3d origin, Vec3d direction, float length, float width) {
        this(ENTITY_TYPE, world);
        this.setPosition(origin.x, origin.y, origin.z);
        var horizontalLength = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        this.setYaw((float) (MathHelper.atan2(-direction.x, direction.z) * 57.2957763671875));
        this.setPitch((float) (MathHelper.atan2(-direction.y, horizontalLength) * 57.2957763671875));
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getDataTracker().set(LENGTH, length);
        this.getDataTracker().set(WIDTH, width);
    }

    @Override
    protected void initDataTracker() {
        // 1.20.1 has no `DataTracker.Builder`; defaults are declared on the tracker itself.
        this.dataTracker.startTracking(WIDTH, 0.5F);
        this.dataTracker.startTracking(LENGTH, 1.0F);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (data.equals(WIDTH) || data.equals(LENGTH)) {
            this.calculateDimensions();
        }
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        // Origin-centered box sized to the beam's reach, since the beam extends outward
        // from this entity's position rather than being centered on it like a normal entity.
        var reach = Math.max(getBeamLength(), getBeamWidth());
        return EntityDimensions.changing(reach, reach);
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient && this.age >= LIFESPAN_TICKS) {
            discard();
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    public float getBeamWidth() {
        return this.getDataTracker().get(WIDTH);
    }

    public float getBeamLength() {
        return this.getDataTracker().get(LENGTH);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 128.0 * 128.0;
    }
}
