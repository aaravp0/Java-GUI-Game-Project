/*
Aarav Prakash
The Night Of Barney
A horror game, where Barney and his friends try to kill you, and you have to escape.
(make sure to install the font that is in the fonts folder)
*/

import javax.swing.*;

//ecompasses the entire program
public class NightOfBarney extends JFrame
{
    // has code for the music and frame
    public NightOfBarney()
    {
        JFrame frame = new JFrame("The Night Of Barney");
        frame.setSize(800, 800);
        Manager man = new Manager();
        frame.setContentPane(man);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    // calls constructor
    public static void main(String[] args)
    {
        new NightOfBarney();
    }
}
/*
 * Barney game over image: Alexander Chu, using AI
 * https://www.pngkit.com/view/u2w7t4q8r5r5w7q8_braid-sprite-sheet-pixel-art-
 * gifs-animation-game/
 * https://nyknck.itch.io/citypackpixelart
 * https://www.reddit.com/r/PixelArt/comments/erx2xf/cc_grass_sprite/
 * https://www.reddit.com/r/PixelArt/comments/e00jf7/one_top_down_house_from_4_directions/
 * https://twitter.com/Grandero_Pixels/status/1169072413570752512
 * https://free-game-assets.itch.io/free-green-zone-tileset-pixel-art
 * https://www.pixilart.com/draw/gas-can-0d8faf0067e0f64
 * https://www.pixilart.com/art/birds-eye-view-shot-forest-6bf06cad21ccc4b
 * https://www.spriters-resource.com/game_boy_advance/pokemonrubysapphire/sheet/8192/
 * https://cookieconnection.juliausher.com/resource/barney
 * https://www.dafont.com/craftron-gaming.d6128
 * https://www.vecteezy.com/free-vector/horizontal-road
 * https://www.pinterest.com/pin/788692953476885743/
 * https://www.artstation.com/artwork/KaP0nG
 * https://www.youtube.com/watch?v=CqOPkeYyDt4
 * https://www.kindpng.com/imgv/hTTxJbo_preview-pixel-art-character-sprite-sheet-hd-png/
 * https://sanctumpixel.itch.io/forest-top-down-pixel-art-tileset
 */
