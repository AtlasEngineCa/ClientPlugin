package ca.atlasengine.gui.windows;

import ca.atlasengine.cutscenes.CutsceneManager;
import ca.atlasengine.cutscenes.CutsceneSubFrame;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class CutsceneGUI extends LightweightGuiDescription {
    private final List<CutsceneSubFrame> subFrames = new ArrayList<>();
    private final WPlainPanel root;
    private final int sizeX;
    private final int sizeY;
    private WListPanel<CutsceneSubFrame, CutsceneSubFrameSelect> list;

    BiConsumer<CutsceneSubFrame, CutsceneSubFrameSelect> configurator = (CutsceneSubFrame s, CutsceneSubFrameSelect destination) -> {
        destination.yaw.setText(String.valueOf(s.yaw()));
        destination.pitch.setText(String.valueOf(s.pitch()));
        destination.transitionTime.setText(String.valueOf(s.transitionTime()));
        destination.waitTime.setText(String.valueOf(s.waitTime()));

        destination.yaw.setChangedListener(s::setYaw);
        destination.pitch.setChangedListener(s::setPitch);
        destination.transitionTime.setChangedListener(s::setTransitionTime);
        destination.waitTime.setChangedListener(s::setWaitTime);

        destination.target.setOnClick(() -> {
            CutsceneManager.sendCapture(subFrames.indexOf(s));
            MinecraftClient.getInstance().setScreen(null);
        });

        if (subFrames.indexOf(s) == 0) {
            destination.moveUp.setEnabled(false);
        }

        if (subFrames.indexOf(s) == subFrames.size() - 1) {
            destination.moveDown.setEnabled(false);
        }

        if (subFrames.size() == 1) {
            destination.remove.setEnabled(false);
        } else {
            destination.remove.setOnClick(() -> {
                subFrames.remove(s);
                drawSubFrames();
            });
        }

        destination.moveUp.setOnClick(() -> {
            Collections.swap(subFrames, subFrames.indexOf(s), subFrames.indexOf(s) - 1);
            drawSubFrames();
        });

        destination.moveDown.setOnClick(() -> {
            Collections.swap(subFrames, subFrames.indexOf(s), subFrames.indexOf(s) + 1);
            drawSubFrames();
        });
    };


    private static class CutsceneSubFrameSelect extends WPlainPanel {
        public WTextField yaw;
        public WTextField pitch;
        public WTextField transitionTime;
        public WTextField waitTime;
        public WButton remove;

        public WButton moveUp;
        public WButton moveDown;

        public WButton target;

        public CutsceneSubFrameSelect() {
            yaw = new WTextField(Text.of("Yaw"));
            pitch = new WTextField(Text.of("Pitch"));
            transitionTime = new WTextField(Text.of("Transition"));
            waitTime = new WTextField(Text.of("Wait"));
            remove = new WButton(Text.of("X"));
            moveUp = new WButton(Text.of("▲"));
            moveDown = new WButton(Text.of("▼"));

            target = new WButton(Text.of("Capture"));

            this.add(yaw, 3, 2, 50, 18);
            this.add(pitch, 55, 2, 50, 18);

            this.add(target, 110, 2, 50, 18);

            this.add(waitTime, 168, 2, 40, 18);
            this.add(transitionTime, 210, 2, 40, 18);

            this.add(moveUp, 264, 2, 20, 18);
            this.add(moveDown, 284, 2, 20, 18);
            this.add(remove, 314, 2, 20, 18);

            this.setSize(5*18, 100);
            this.setBackgroundPainter(BackgroundPainter.createColorful(0x3F000000));
        }
    }

    public CutsceneGUI(PacketByteBuf buf) {
        UUID id = buf.readUuid();

        int frames = buf.readInt();
        this.subFrames.clear();

        for (int i = 0; i < frames; ++i) {
            buf.readInt();
            subFrames.add(new CutsceneSubFrame(buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt()));
        }

        WPlainPanel root = new WPlainPanel();
        this.root = root;
        setRootPanel(root);

        this.sizeX = 384;
        this.sizeY = 216;
        root.setSize(sizeX, sizeY);

        WLabel label = new WLabel(Text.of("Path (" + id + ")"));
        root.add(label, 16, 16, 5, 1);

        WLabel yawLabel = new WLabel(Text.of("Yaw"));
        WLabel pitchLabel = new WLabel(Text.of("Pitch"));
        WLabel transitionLabel = new WLabel(Text.of("Transition"));
        WLabel waitLabel = new WLabel(Text.of("Wait"));

        root.add(yawLabel, 19, 40, 50, 18);
        root.add(pitchLabel, 73, 40, 50, 18);
        root.add(transitionLabel, 210 + 19, 40, 40, 18);
        root.add(waitLabel, 168 + 19, 40, 40, 18);

        WButton add = new WButton(Text.of("+"));
        add.setOnClick(() -> {
            subFrames.add(new CutsceneSubFrame( 0, 0, 1, 1));
            CutsceneManager.sendSave(subFrames);
            drawSubFrames();
        });

        root.add(add, 16, 60 + sizeY - 90 + 3, 20, 20);

        WButton save = new WButton(Text.of("Save"));
        save.setOnClick(() -> {
            CutsceneManager.sendSave(subFrames);
            MinecraftClient.getInstance().setScreen(null);
        });

        root.add(save, sizeX - 90, 60 + sizeY - 90 + 3, 80, 100);

        drawSubFrames();
        root.validate(this);
    }

    private void drawSubFrames() {
        if (this.list != null) {
            root.remove(this.list);
        }

        this.list = new WListPanel<>(subFrames, CutsceneSubFrameSelect::new, configurator);
        this.list.setListItemHeight(24);
        root.add(list, 14, 48, sizeX - 32, sizeY - 94);

        root.validate(this);
    }
}
