package jogoaps;

import javax.swing.JFrame;
import jogoaps.modelo.Level;

public class Container extends JFrame {

		public Container() {
			
			add(new Level());
			setTitle("Jogo APS"); //T�tulo da tela
			setSize(700,429); // Tamanho da tela
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sair da tela ao clicar no X
			setLocationRelativeTo(null); //Vai aparecer a tela no meio
			this.setResizable(false); // n�o mudar resoulu��o da tela
			setVisible(true);
			
			
		}
		
		public static void main(String[] args) {
			
			new Container();
			
			
			
			
			
			
		}
}
