package tk.minersonline.mineways.client;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.OrderedText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import tk.minersonline.mineways.MineWaysMod;
import tk.minersonline.mineways.block.directionalSign.DirectionalSignBlock;
import tk.minersonline.mineways.block.directionalSign.DirectionalSignBlockEntity;

import java.util.List;

public class DirectionalSignBlockEntityRenderer implements BlockEntityRenderer<DirectionalSignBlockEntity> {
	private static final String STICK = "stick";
	private static final int GLOWING_BLACK_COLOR = -988212;
	private static final int RENDER_DISTANCE = MathHelper.square(16);
	private final TextRenderer textRenderer;
	private final ModelPart model;
	public DirectionalSignBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.textRenderer = ctx.getTextRenderer();


		// Load the model from a JSON file
		ModelLoader modelLoader = new ModelLoader();

		// Load a BakedModel
		UnbakedModel unbakedModel = modelLoader.getOrLoadModel(new Identifier(MineWaysMod.MOD_ID, "block/directional_sign_base"));
		unbakedModel.

		// Covert into Model Part.
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		root.addChild("main", bakedModel);

		// Build the final model
		this.model = modelData.getRoot().createPart(16, 16);
	}

	@Override
	public void render(DirectionalSignBlockEntity signBlock, float f, MatrixStack stack, VertexConsumerProvider provider, int light, int overlay) {
		int renderedLight;
		boolean shouldRender;
		int color;
		BlockState blockState = signBlock.getCachedState();
		stack.push();
		float rotation;
		SignType signType = DirectionalSignBlockEntityRenderer.getSignType(blockState.getBlock());
		if (blockState.getBlock() instanceof DirectionalSignBlock) {
			stack.translate(0.5, 0.5, 0.5);
			rotation = -((float)(blockState.get(DirectionalSignBlock.ROTATION) * 360) / 16.0f);
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation));
		}





		float textScale = 0.010416667f;
		stack.translate(0.0, 0.3333333432674408, 0.046666666865348816);
		stack.scale(textScale, -textScale, textScale);
		int rawColor = DirectionalSignBlockEntityRenderer.getColor(signBlock);
		int fontSize = 20;
		OrderedText[] orderedTexts = signBlock.updateSign(MinecraftClient.getInstance().shouldFilterText(), text -> {
			List<OrderedText> list = this.textRenderer.wrapLines(text, 90);
			return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
		});
		if (signBlock.isGlowingText()) {
			color = signBlock.getTextColor().getSignColor();
			shouldRender = DirectionalSignBlockEntityRenderer.shouldRender(signBlock, color);
			renderedLight = 0xF000F0;
		} else {
			color = rawColor;
			shouldRender = false;
			renderedLight = light;
		}
		for (int row = 0; row < 4; ++row) {
			OrderedText orderedText = orderedTexts[row];
			float textWidth = -this.textRenderer.getWidth(orderedText) / 2f;
			if (shouldRender) {
				this.textRenderer.drawWithOutline(orderedText, textWidth, row * 10 - fontSize, color, rawColor, stack.peek().getPositionMatrix(), provider, renderedLight);
				continue;
			}
			this.textRenderer.draw(orderedText, textWidth, (float)(row * 10 - fontSize), color, false, stack.peek().getPositionMatrix(), provider, false, 0, renderedLight);
		}
		stack.pop();
	}

	private static boolean shouldRender(DirectionalSignBlockEntity sign, int signColor) {
		if (signColor == DyeColor.BLACK.getSignColor()) {
			return true;
		}
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
		if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson() && clientPlayerEntity.isUsingSpyglass()) {
			return true;
		}
		Entity entity = minecraftClient.getCameraEntity();
		return entity != null && entity.squaredDistanceTo(Vec3d.ofCenter(sign.getPos())) < (double)RENDER_DISTANCE;
	}

	private static int getColor(DirectionalSignBlockEntity sign) {
		int signColor = sign.getTextColor().getSignColor();
		double d = 0.4;
		int r = (int)((double)NativeImage.getRed(signColor) * d);
		int g = (int)((double)NativeImage.getGreen(signColor) * d);
		int b = (int)((double)NativeImage.getBlue(signColor) * d);
		if (signColor == DyeColor.BLACK.getSignColor() && sign.isGlowingText()) {
			return GLOWING_BLACK_COLOR;
		}
		return NativeImage.packColor(0, b, g, r);
	}

	public static SignType getSignType(Block block) {
		return block instanceof AbstractSignBlock ? ((AbstractSignBlock)block).getSignType() : SignType.OAK;
	}
}
