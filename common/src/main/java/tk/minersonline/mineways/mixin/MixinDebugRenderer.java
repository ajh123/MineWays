package tk.minersonline.mineways.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.minersonline.mineways.item.DeviceLinkerItem;

import java.awt.*;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer {
	//, method = "render(Lnet/minecraft/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;DDD)V"
	@Inject(at = @At("TAIL"), method = "render")
	private void render(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo info) {
//		ClientPlayerEntity player = MinecraftClient.getInstance().player;
//		RenderSystem.disableDepthTest();
//		if (player != null) {
//			ItemStack itemStack = player.getMainHandStack();
////			if (itemStack.getItem() instanceof DeviceLinkerItem) {
//				drawLine(
//					matrices,
//					new Vec3d(20, 64, 20),
//					new Vec3d(10, 64, 10),
//					Color.GREEN,
//					1,
//					player
//				);
////			}
//		}
//		RenderSystem.enableDepthTest();
	}


	private static void drawLine(MatrixStack matrixStack, Vec3d vecStart, Vec3d vecEnd, Color color, float lineWidth, ClientPlayerEntity player)  {
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferbuilder = tessellator.getBuffer();
//
//		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
////		Matrix4f projection = RenderSystem.getProjectionMatrix();
//
//		Vec3d pos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
////		final Frustum frustum = new Frustum(matrix, projection);
////		frustum.setPosition(pos.x, pos.y, pos.z);
//
//		matrixStack.push();
//		matrixStack.translate(-pos.x, -pos.y, -pos.z);
//		RenderSystem.lineWidth(lineWidth);
//
//		bufferbuilder.begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
//		drawLine(matrix, bufferbuilder, color, vecStart, vecEnd);
//		tessellator.draw();
//
//		RenderSystem.lineWidth(1);
//		matrixStack.pop();
	}

	/**
	 * Draw a coloured line from a starting vertex to an end vertex
	 * @param matrixPos the current transformation matrix
	 * @param renderBuffer the buffer builder used to draw the line
	 * @param p1 the position to start from
	 * @param p2 the position to end at
	 */
	private static void drawLine(Matrix4f matrixPos, BufferBuilder renderBuffer, Color color, Vec3d p1, Vec3d p2) {
//		renderBuffer.vertex(matrixPos, (float)p1.x + 0.5f, (float)p1.y + 0.5f, (float)p1.z + 0.5f)
//				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
//				.next();
//		renderBuffer.vertex(matrixPos, (float)p2.x + 0.5f, (float)p2.y + 0.5f, (float)p2.z + 0.5f)
//				.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
//				.next();
	}
}
