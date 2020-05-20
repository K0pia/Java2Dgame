package jogoaps.modelo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Level extends JPanel implements ActionListener {

	private Image fundo;
	private Jogador jogador;
	private Timer timer;
	private List<Inimigo> inimigo;
	private boolean emJogo;
	private int i;

	public Level() {

		setFocusable(true);
		setDoubleBuffered(true);

		ImageIcon referencia = new ImageIcon("arquivos\\background.png");
		fundo = referencia.getImage();

		jogador = new Jogador();
		jogador.load();

		addKeyListener(new TecladoAdapter());

		timer = new Timer(10, this);
		timer.start();
		
		inicializainimigos();
		emJogo = true;

	}

	public void inicializainimigos() {
		int cordenadas[] = new int[1000]; // mudar número de inimigos
		inimigo = new ArrayList<Inimigo>();

		for (int i = 0; i < cordenadas.length; i++) {
			int x = (int) (Math.random() * 100000 + 700); // onde aparece os inimigos
			int y = (int) (Math.random() * 400 + 10);
			inimigo.add(new Inimigo(x, y));
		}
	}


	public void paint(Graphics g) {
		
		Graphics2D graficos = (Graphics2D) g;
		if (emJogo == true) {
		graficos.drawImage(fundo, 0, 0, null);
			
		graficos.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(), this);
		
		List <Tiro> tiros = jogador.getTiros();
		for(int u = 0; u < tiros.size();u++) {
			Tiro m = tiros.get(u);
			m.load();
			graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);
			
		}
		
		for (int o = 0; o < inimigo.size(); o++) {
			Inimigo in = inimigo.get(o);
			in.load();
			graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);
		}	
		}
		else {
			ImageIcon fimJogo = new ImageIcon("arquivos\\fimdejogo.png");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}
		
		if (i > 29) {
			ImageIcon fimJogo = new ImageIcon("arquivos\\TelaWin.jpg");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}
		
		g.dispose();
		
			
}

	@Override
	public void actionPerformed(ActionEvent e) {

		jogador.update();

		List<Tiro> tiros = jogador.getTiros();
		for (int i = 0; i < tiros.size(); i++) {
			Tiro m = tiros.get(i);
			if (m.isVisivel()) {
				m.update();
			} else {
				tiros.remove(i);
			}
		}

		for (int o = 0; o < inimigo.size(); o++) {
			Inimigo in = inimigo.get(o);
			if (in.isVisivel()) {
				in.update();
			} else {
				inimigo.remove(o);
			}
		}
		constatarColisoes();
		repaint();

	}

	private void constatarColisoes() {
		Rectangle formaNave = jogador.getBounds();
		Rectangle formaInimigo;
		Rectangle formaTiro;

		for (int i = 0; i < inimigo.size(); i++) {
			Inimigo tempInimigo = inimigo.get(i);
			formaInimigo = tempInimigo.getBounds();
			if (formaNave.intersects(formaInimigo)) {
				jogador.setVisivel(false);
				tempInimigo.setVisivel(false);
				emJogo = false;
			}
			
		
		}
		
		
		List<Tiro> tiros = jogador.getTiros();
		for (int j = 0; j < tiros.size(); j++) {
			Tiro tempTiro = tiros.get(j);
			formaTiro = tempTiro.getBounds();

			for (int o = 0; o < inimigo.size(); o++) {
				Inimigo tempInimigo = inimigo.get(o);
				formaInimigo = tempInimigo.getBounds();

				if (formaTiro.intersects(formaInimigo)) {
					tempInimigo.setVisivel(false);
					tempTiro.setVisivel(false);
					i++;
				}
			}
		}
		

	}

	private class TecladoAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			jogador.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			jogador.keyRelease(e);
		}

	}

}
