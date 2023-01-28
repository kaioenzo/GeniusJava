package org.example.main;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.example.controller.AlbumController;
import org.example.controller.ArtistasController;
import org.example.controller.MusicaController;
import org.example.controller.ProdutorController;
import org.example.model.Album;
import org.example.model.Artista;
import org.example.model.Atribuicao;
import org.example.model.Musica;
import org.example.model.PortalDeMusica;
import org.example.model.Produtor;
import org.example.view.AlbumsPanel;
import org.example.view.ArtistaPanel;
import org.example.view.MusicasPanel;
import org.example.view.ProdutorPanel;
import org.example.view.components.RoundedButtonMenu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Classe de entrada para o programa, onde a ‘interface’ é carregada e os dados iniciais são cadastrados.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class MainFrame extends JFrame {

    final JPanel menuPanel = new JPanel();
    final JPanel infoPanel = new JPanel();
    final MusicasPanel musicaPanel = new MusicasPanel();
    final ArtistaPanel artistaPanel = new ArtistaPanel();
    final ProdutorPanel produtoresPanel = new ProdutorPanel();
    final AlbumsPanel albumsPanel = new AlbumsPanel();
    final CardLayout cl = new CardLayout();
    final JButton musicasButton = new RoundedButtonMenu("Músicas");
    final JButton artistasButton = new RoundedButtonMenu("Artistas");
    final JButton albumsButton = new RoundedButtonMenu("Albums");
    final JButton produtoreButton = new RoundedButtonMenu("Produtores");

    public MainFrame(String titulo) {
        super(titulo);
        setLayout();

    }

    public void setLayout() {
        setSize(1200, 600);
        setLayout(new BorderLayout());

        // menu lateral
        JLabel imageLabel;
        try {
            URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/5/51/Genius-logo.png");
            BufferedImage image = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(image);
            Image imageScaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(imageScaled));

        } catch (IOException e) {
            System.out.println(e);
            imageLabel = new JLabel("Genius");
        }
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weighty = 0.5;

        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(imageLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        musicasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(musicasButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        artistasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(artistasButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        produtoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(produtoreButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        albumsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(albumsButton, constraints);

        infoPanel.setLayout(cl);
        infoPanel.setSize(600, 800);
        infoPanel.add(musicaPanel, "1");
        infoPanel.add(artistaPanel, "2");
        infoPanel.add(produtoresPanel, "3");
        infoPanel.add(albumsPanel, "4");
        cl.show(infoPanel, "1");

        add(menuPanel, BorderLayout.WEST);

        add(infoPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        artistasButton.addActionListener(e -> cl.show(infoPanel, "2"));
        musicasButton.addActionListener(e -> cl.show(infoPanel, "1"));
        produtoreButton.addActionListener(e -> cl.show(infoPanel, "3"));
        albumsButton.addActionListener(e -> cl.show(infoPanel, "4"));
    }

    public static void main(String[] args) {
        iniciarGeniusJava();
        MainFrame frame = new MainFrame("Genius Java");
    }

    /**
     * Método que adiciona informações ao sistema.
     */
    public static void iniciarGeniusJava() {
        // Única instancia do objeto PortalDeMusica
        PortalDeMusica bd = PortalDeMusica.getInstance();

        // Controllers
        AlbumController albumController = new AlbumController();
        MusicaController musicaController = new MusicaController();
        ArtistasController artistasController = new ArtistasController();
        ProdutorController produtorController = new ProdutorController();

        // cadastra o genero e recupera pelo nome, e cadastra artista
        bd.cadastrarGenero("Pop", "Bom pra chorar");
        var genero = bd.getGeneroPeloNome("Pop");
        genero.
                ifPresent(generoMusical ->
                        artistasController.adicionar(new Artista(artistasController.getProximoId(), "Taylor Swift",
                                LocalDate.now(),
                                "A loirinha manda muito",
                                "pop")));

        // recupera artista pelo nome
        var artista = artistasController.get("Taylor Swift");

        // cadastra produtor e recupera pelo nome
        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "Shellback", LocalDate.now(), "Karl " +
                "Johan " +
                "Schuster, known " +
                "famously " +
                "as Shellback, " +
                "is a Swedish songwriter, record producer, and musician.", Atribuicao.EXECUTIVO));
        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "metro", LocalDate.now(), "Karl " +
                "Johan " +
                "Schuster, known famously " +
                "as " +
                "Shellback, is a " +
                "Swedish songwriter, record producer, and musician.", Atribuicao.EXECUTIVO));
        var produtor = produtorController.get("Shellback");

        // cadastra música
        ArrayList<Artista> artistas = new ArrayList<>(artista);
        ArrayList<Produtor> produtores = new ArrayList<>(produtor);
        ArrayList<String> generos = new ArrayList<>(Collections.singleton("pop"));

        musicaController.adicionar(new Musica(musicaController.getProximoId(), "Shake it off", new HashSet<>(generos), """

                I stay out too late
                Got nothing in my brain
                That's what people say
                That's what people say
                I go on too many dates
                But I can't make them stay
                At least that's what people say
                That's what people say
                But I keep cruising
                Can't stop, won't stop moving
                It's like I got this music in my mind
                Saying it's gonna be alright
                I never miss a beat
                I'm lightning on my feet
                And that's what they don't see
                That's what they don't see
                Players gonna play, play, play, play, play
                And the haters gonna hate, hate, hate, hate, hate (haters gonna hate)
                Baby, I'm just gonna shake, shake, shake, shake, shake
                I shake it off, I shake it off
                Heartbreakers gonna break
                Fakers gonna fake
                I'm just gonna shake
                I shake it off, I shake it off
                I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I, shake it off, I shake it off
                I, I, I, shake it off, I shake it off""",
                new HashSet<>(artistas),
                new HashSet<>(produtores)));
        musicaController.adicionar(new Musica(musicaController.getProximoId(), "Lover", new HashSet<>(generos), """

                I stay out too late
                Got nothing in my brain
                That's what people say
                That's what people say
                I go on too many dates
                But I can't make them stay
                At least that's what people say
                That's what people say
                But I keep cruising
                Can't stop, won't stop moving
                It's like I got this music in my mind
                Saying it's gonna be alright
                I never miss a beat
                I'm lightning on my feet
                And that's what they don't see
                That's what they don't see
                Players gonna play, play, play, play, play
                And the haters gonna hate, hate, hate, hate, hate (haters gonna hate)
                Baby, I'm just gonna shake, shake, shake, shake, shake
                I shake it off, I shake it off
                Heartbreakers gonna break
                Fakers gonna fake
                I'm just gonna shake
                I shake it off, I shake it off
                I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I, shake it off, I shake it off
                I, I, I, shake it off, I shake it off""",
                new HashSet<>(artistas),
                new HashSet<>(produtores)));

        var musica = musicaController.get("Shake it off");

        var musicas = new ArrayList<Musica>();
        musicas.add(musica.get(0));

        // cadastra album e recupera pelo nome
        albumController.adicionar(new Album(albumController.getProximoId(), "1989", LocalDate.now(), musicas));

        // cadastra mais informações
        artistasController.adicionar(new Artista(artistasController.getProximoId(), "Kanye West", LocalDate.now(),
                "Artista de rap", "hip-gop"));
        artistasController.adicionar(new Artista(artistasController.getProximoId(), "PARTYNEXTDOOR", LocalDate.now(),
                "Artista de rap alternativo", "hip-gop"));
        artistasController.adicionar(new Artista(artistasController.getProximoId(), "LiSA", LocalDate.now(),
                "Artista japoensa", "jpop"));
        artistasController.adicionar(new Artista(artistasController.getProximoId(), "Tyler, the creator",
                LocalDate.now(),
                "Artista americano de r&b", "R&B"));

        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "NO ID", LocalDate.now(),
                "Produtor icônico, já produziu mais de 1000 músicas", Atribuicao.ENGENHEIRODESOM));
        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "Dr.DRE", LocalDate.now(),
                "Produtor lendário", Atribuicao.EXECUTIVO));
        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "Okinawa", LocalDate.now(),
                "Produtora japonesa", Atribuicao.EXECUTIVO));

        var artistasAlbumKanye = new ArrayList<Artista>();
        artistasAlbumKanye.addAll(artistasController.get("PARTYNEXTDOOR"));
        artistasAlbumKanye.addAll(artistasController.get("Kanye West"));
        var produtoresAlbumKanye = new ArrayList<Produtor>();
        produtoresAlbumKanye.add(produtorController.get("NO ID").get(0));
        musicaController.adicionar(
                new Musica(musicaController.getProximoId(),
                        "Ghost Town",
                        new HashSet<>(Collections.singleton("hip-hop")),
                        "Some day, some day\n" +
                                "Some day I'll, I wanna wear a starry crown\n" +
                                "Some day, some day\n" +
                                "Some day I wanna lay down, like God did, on Sunday\n" +
                                "Hold up, hold up\n" +
                                "Some days, some days\n" +
                                "I remember this on Sunday\n" +
                                "Back way, yeah way, way\n" +
                                "Some day, mmm, mmm\n" +
                                "Some day, I wanna tell everybody, some days\n" +
                                "I wanna hit the red dot, I'll never find\n" +
                                "Some days, some\n" +
                                "Each one, knowing how 'm livin', smokin' marijuana\n" +
                                "Now I'm livin' high, doin' what I wanna, some days\n" +
                                "I've been tryin' to make you love me\n" +
                                "But everything I try just takes you further from me\n" +
                                "Some day we gon' set it off\n" +
                                "Some day we gon' get this off\n" +
                                "Baby, don't you bet it all\n" +
                                "On a pack of Fentanyl\n" +
                                "You might think they wrote you off\n" +
                                "They gon' have to rope me off\n" +
                                "Some day the drama'll be gone\n" +
                                "And they'll pray, it's not enough\n" +
                                "Sometimes I take all the shine\n" +
                                "Talk like I drank all the wine\n" +
                                "Years ahead but way behind\n" +
                                "I'm on one, two, three, four, five\n" +
                                "No half-truths, just naked minds\n" +
                                "Caught between space and time\n" +
                                "This not what we had in mind\n" +
                                "But maybe some day\n" +
                                "I've been tryin' to make you love me\n" +
                                "But everything I try just takes you further from me\n" +
                                "Oh, once again I am a child\n" +
                                "I let it all go (go), of everything that I know, yeah\n" +
                                "Of everything that I know, yeah\n" +
                                "And nothing hurts anymore, I feel kinda free\n" +
                                "We're still the kids we used to be, yeah, yeah\n" +
                                "I put my hand on the stove, to see if I still bleed\n" +
                                "Yeah, and nothing hurts anymore, I feel kinda free\n" +
                                "We're still the kids we used to be, yeah, yeah\n" +
                                "I put my hand on the stove, to see if I still bleed\n" +
                                "Yeah, and nothing hurts anymore, I feel kinda free\n" +
                                "We're still the kids we used to be, yeah, yeah\n" +
                                "I put my hand on the stove, to see if I still bleed\n" +
                                "Yeah, and nothing hurts anymore, I feel kinda free\n" +
                                "We're still the kids we used to be, yeah, yeah\n" +
                                "I put my hand on the stove, to see if I still bleed\n" +
                                "Yeah, and nothing hurts anymore, I feel kinda free",
                        new HashSet<>(artistasAlbumKanye),
                        new HashSet<>(produtoresAlbumKanye)
                ));

        var artistaAlbumLisa = new ArrayList<Artista>(artistasController.get("LiSA"));
        var produtorMusicaLisa = new ArrayList<Produtor>();
        produtorMusicaLisa.add(produtorController.get("Okinawa").get(0));
        musicaController.adicionar(
                new Musica(musicaController.getProximoId(),
                        "Gurenge",
                        new HashSet<>(Collections.singleton("hip-hop")),
                        "強くなれる理由を知った 僕を連れて進め\n" +
                                "泥だらけの走馬灯に酔う こわばる心\n" +
                                "震える手は掴みたいものがある それだけさ\n" +
                                "夜の匂いに (I'll spend all thirty nights)\n" +
                                "空睨んでも (Staring into the sky)\n" +
                                "変わっていけるのは自分自身だけ それだけさ\n" +
                                "強くなれる理由を知った 僕を連れて進め\n" +
                                "どうしたって！\n" +
                                "消せない夢も 止まれない今も\n" +
                                "誰かのために強くなれるなら\n" +
                                "ありがとう 悲しみよ\n" +
                                "世界に打ちのめされて 負ける意味を知った\n" +
                                "紅蓮の華よ咲き誇れ！ 運命を照らして\n" +
                                "イナビカリの雑音が耳を刺す 戸惑う心\n" +
                                "優しいだけじゃ守れないものがある？ わかってるけど\n" +
                                "水面下で絡まる善悪 透けて見える偽善に天罰\n" +
                                "Tell me why, Tell me why, Tell me why, Tell me...\n" +
                                "I don't need you!\n" +
                                "逸材の花より 挑み続け咲いた一輪が美しい\n" +
                                "乱暴に敷き詰められた トゲだらけの道も\n" +
                                "本気の僕だけに現れるから 乗り越えてみせるよ\n" +
                                "簡単に片付けられた 守れなかった夢も\n" +
                                "紅蓮の心臓に根を生やし この血に宿って\n" +
                                "人知れず儚い 散りゆく結末\n" +
                                "無情に破れた 悲鳴の風吹く\n" +
                                "誰かの笑う影 誰かの泣き声\n" +
                                "誰もが幸せを願ってる\n" +
                                "どうしたって！\n" +
                                "消せない夢も 止まれない今も\n" +
                                "誰かのために強くなれるなら\n" +
                                "ありがとう 悲しみよ\n" +
                                "世界に打ちのめされて 負ける意味を知った\n" +
                                "紅蓮の華よ咲き誇れ！ 運命を照らして\n" +
                                "運命を照らして",
                        new HashSet<>(artistaAlbumLisa),
                        new HashSet<>(produtorMusicaLisa)
                )); musicaController.adicionar(
                new Musica(musicaController.getProximoId(),
                        "cancellation",
                        new HashSet<>(Collections.singleton("hip-hop")),
                        "gongu no ne ga hibiitara\n" +
                                "dare no sei ni mo dekinai kara\n" +
                                "sekinin kitai seiron jogen sae mo ima wa mimizawari\n" +
                                "fukai kokyuu de kakikeshita\n" +
                                "\n" +
                                "(Nervous?) Click clack (shaking) smash, knock out\n" +
                                "kodoku ga faitingu songu\n" +
                                "\n" +
                                "Ready box\n" +
                                "Bang bang bang bang uchitsukeru haato biito sukuriimu\n" +
                                "(Can you hear your heart to beat?)\n" +
                                "bones bones bones bones okuba ga kishimu haamoni\n" +
                                "guchi wo haite tatakaenai yatsu ni\n" +
                                "kuishibatte tatakatteru boku no kimochi nante wakare hazu ga nai ya\n" +
                                "Noise cancellation\n" +
                                "\n" +
                                "yugaru kage donna toki mo kagami ni utsuru yowai boku wo taoshite kitan dakara\n" +
                                "nijimu ase nigirishimeta kobushi no kizu no kazu wa honki ni natta akashi\n" +
                                "\n" +
                                "Bang bang bang bang uchitsukeru haato biito sukuriimu\n" +
                                "(Can you hear your heart to beat?)\n" +
                                "Bones bones bones bones okuba ga kishimu haamoni\n" +
                                "kore shika nai to iikirenai yatsu ga\n" +
                                "kore demo ka to sugaritsuku boku to ringu ni tatanaide\n" +
                                "Noise cancellation\n" +
                                "\n" +
                                "namari no you ni omoi panchi kurau tabi ni\n" +
                                "dareka no omoi no tsuyosa wo shitta\n" +
                                "chi darake no bokura ni iitai koto ga aru nara\n" +
                                "koko made kite yatte misero yo zako wa blocks way\n" +
                                "\n" +
                                "Ready box\n" +
                                "Bang bang bang bang uchitsukeru haato biito sukuriimu\n" +
                                "(Can you hear your heart to beat?)\n" +
                                "Bones bones bones bones okuba ga kishimu haamoni\n" +
                                "guchi wo haite tatakaenai yatsu ni\n" +
                                "kuishibatte tatakatteru boku no kimochi nante wakannai na\n" +
                                "honki wo narase hibike soul sound\n" +
                                "Noise cancellation\n" +
                                "\n" +
                                "hikaru hou e susumu dake\n" +
                                "Now it's showtime",
                        new HashSet<>(artistaAlbumLisa),
                        new HashSet<>(produtorMusicaLisa)
                ));
        var musicasDaLisa =
                new ArrayList<>(musicaController.getMusicasAssociadas(4));
        albumController.adicionar(
                new Album(albumController.getProximoId(),
                        "Leo-Nine",
                        LocalDate.now(),
                        musicasDaLisa));
    }

}
