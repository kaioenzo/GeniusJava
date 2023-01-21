package view;

import controller.PessoaBaseController;
import model.Produtor;


public class ProdutorDialog extends BasePessoaDialog<Produtor> {

    public ProdutorDialog(PessoaBaseController<Produtor> controller, int id, String label1, String label2, String label3) {
        super(controller, id, label1, label2, label3);
    }
}
