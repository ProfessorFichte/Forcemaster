package net.forcemaster_rpg.client.entity;

import net.forcemaster_rpg.entity.NenSphereBeamEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.client.render.BeamRenderer;
import net.spell_engine.client.util.Color;

public class NenSphereBeamRenderer extends EntityRenderer<NenSphereBeamEntity> {
    private static final String TEXTURE_ID = "textures/entity/beacon_beam.png";
    private static final float FLOW = 4.0F;
    private static final long ARCANE_COLOR_RGBA = Color.ARCANE.toRGBA();

    public NenSphereBeamRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(NenSphereBeamEntity entity) {
        return null;
    }

    @Override
    public void render(NenSphereBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                        VertexConsumerProvider vertexConsumers, int light) {
        var length = entity.getBeamLength();
        var width = entity.getBeamWidth();
        if (length <= 0F) {
            return;
        }

        matrices.push();

        var direction = Vec3d.fromPolar(entity.getPitch(), entity.getYaw());
        var pitchFromVertical = (float) Math.acos(direction.y);
        var horizontalAngle = (float) Math.atan2(direction.z, direction.x);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((1.5707964F - horizontalAngle) * 57.295776F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitchFromVertical * 57.295776F));

        var texture = Identifier.of(TEXTURE_ID);
        var renderLayers = BeamRenderer.layerSetFor(texture, Spell.Target.Beam.Luminance.HIGH);
        var color = Color.IntFormat.fromLongRGBA(ARCANE_COLOR_RGBA);

        BeamRenderer.renderBeam(matrices, vertexConsumers,
                entity.getWorld().getTime(), tickDelta, FLOW, true,
                color, color, renderLayers,
                0, length, width);

        matrices.pop();
    }
}
