package io.github.shotoh.katclient.features.general;

import gg.essential.universal.UChat;
import io.github.shotoh.katclient.core.KatClientConfig;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrystalHollowsScan {
    private static final ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
    private static final ArrayList<BlockPos> coords = new ArrayList<>();
    private static boolean cancel = false;

    public static void runCommand() {
        cancel = false;
        pool.schedule(CrystalHollowsScan::run, 0, TimeUnit.SECONDS);
    }

    public static void run() {
        long start = System.currentTimeMillis();
        UChat.chat("&7Scanning Crystal Hollows...");

        boolean throneFound = false;
        boolean spiralFound = false;
        boolean corleoneFound = false;

        IBlockState torchBlockState = Blocks.torch.getDefaultState();

        Minecraft mc = Minecraft.getMinecraft();
        WorldClient world = mc.theWorld;

        coords.clear();

        for (int i = 512; i < 824; i++) {
            for (int j = 30; j < 170; j++) {
                for (int k = 512; k < 824; k++) {
                    BlockPos pos = new BlockPos(i, j, k);
                    if (world.getBlockState(pos) == torchBlockState) {
                        coords.add(pos);
                    }
                }
            }
        }

        for (BlockPos pos : coords) {
            throneFound = scanThrone(world, pos, throneFound);
            spiralFound = scanSpiral(world, pos, spiralFound);
            corleoneFound = scanCorleone(world, pos, corleoneFound);
        }

        if (KatClientConfig.timeToScan) {
            UChat.chat("&5TTS:&r&d " + (System.currentTimeMillis() - start) + " ms");
        }
        if (!throneFound && !spiralFound && !corleoneFound) {
            UChat.chat("&cFailed to find structures, rescanning in 10 seconds");
            if (!cancel) {
                pool.schedule(CrystalHollowsScan::run, 10, TimeUnit.SECONDS);
            }
        }
    }

    public static void stop() {
        UChat.chat("&7Stopping scan...");
        cancel = true;
    }

    public static boolean scanThrone(WorldClient world, BlockPos pos, boolean found) {
        if (found) {
            return true;
        } else {
            IBlockState torchBlockState = Blocks.torch.getDefaultState();
            IBlockState cobblestoneWallBlockState = Blocks.cobblestone_wall.getDefaultState();
            IBlockState cobblestoneBlockState = Blocks.cobblestone.getDefaultState();
            BlockPos newPos;

            newPos = new BlockPos(pos.getX() + 6, pos.getY(), pos.getZ());
            if (world.getBlockState(newPos) != torchBlockState) {
                newPos = new BlockPos(pos.getX() - 6, pos.getY(), pos.getZ());
                if (world.getBlockState(newPos) != torchBlockState) {
                    newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 6);
                    if (world.getBlockState(newPos) != torchBlockState) {
                        newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 6);
                        if (world.getBlockState(newPos) != torchBlockState) {
                            return false;
                        }
                    }
                }
            }

            newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            if (world.getBlockState(newPos) == cobblestoneWallBlockState) {
                newPos = new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ());
                if (world.getBlockState(newPos) == cobblestoneBlockState) {
                    UChat.chat("&5Throne found:&r&d (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")");
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean scanSpiral(WorldClient world, BlockPos pos, boolean found) {
        if (found) {
            return true;
        } else {
            IBlockState torchBlockState = Blocks.torch.getDefaultState();
            IBlockState darkOakFenceBlockState = Blocks.dark_oak_fence.getDefaultState();
            IBlockState cobblestoneBlockState = Blocks.cobblestone.getDefaultState();
            BlockPos newPos;

            newPos = new BlockPos(pos.getX() + 14, pos.getY(), pos.getZ() + 8);
            if (world.getBlockState(newPos) != torchBlockState) {
                newPos = new BlockPos(pos.getX() + 8, pos.getY(), pos.getZ() + 14);
                if (world.getBlockState(newPos) != torchBlockState) {
                    newPos = new BlockPos(pos.getX() + 14, pos.getY(), pos.getZ() - 8);
                    if (world.getBlockState(newPos) != torchBlockState) {
                        newPos = new BlockPos(pos.getX() + 8, pos.getY(), pos.getZ() - 14);
                        if (world.getBlockState(newPos) != torchBlockState) {
                            newPos = new BlockPos(pos.getX() - 14, pos.getY(), pos.getZ() + 8);
                            if (world.getBlockState(newPos) != torchBlockState) {
                                newPos = new BlockPos(pos.getX() - 8, pos.getY(), pos.getZ() + 14);
                                if (world.getBlockState(newPos) != torchBlockState) {
                                    newPos = new BlockPos(pos.getX() - 14, pos.getY(), pos.getZ() - 8);
                                    if (world.getBlockState(newPos) != torchBlockState) {
                                        newPos = new BlockPos(pos.getX() - 8, pos.getY(), pos.getZ() - 14);
                                        if (world.getBlockState(newPos) != torchBlockState) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            if (world.getBlockState(newPos) == darkOakFenceBlockState) {
                newPos = new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ());
                if (world.getBlockState(newPos) == cobblestoneBlockState) {
                    UChat.chat("&5Spiral found:&r&d (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")");
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean scanCorleone(WorldClient world, BlockPos pos, boolean found) {
        if (found) {
            return true;
        } else {
            IBlockState torchBlockState = Blocks.torch.getDefaultState();
            IBlockState cobblestoneWallBlockState = Blocks.cobblestone_wall.getDefaultState();
            IBlockState cyanHardenedClayBlockState = Blocks.hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN);
            BlockPos newPos;

            newPos = new BlockPos(pos.getX() + 6, pos.getY(), pos.getZ());
            if (world.getBlockState(newPos) != torchBlockState) {
                newPos = new BlockPos(pos.getX() - 6, pos.getY(), pos.getZ());
                if (world.getBlockState(newPos) != torchBlockState) {
                    newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 6);
                    if (world.getBlockState(newPos) != torchBlockState) {
                        newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 6);
                        if (world.getBlockState(newPos) != torchBlockState) {
                            return false;
                        }
                    }
                }
            }

            newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            if (world.getBlockState(newPos) == cobblestoneWallBlockState) {
                newPos = new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ());
                if (world.getBlockState(newPos) == cobblestoneWallBlockState) {
                    newPos = new BlockPos(pos.getX(), pos.getY() - 3, pos.getZ());
                    if (world.getBlockState(newPos) == cyanHardenedClayBlockState) {
                        UChat.chat("&5Corleone found:&r&d (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ")");
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
