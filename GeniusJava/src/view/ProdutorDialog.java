package view;

import controller.BaseController;
import model.Produtor;


public class ProdutorDialog extends BaseDialog<Produtor> {

    public ProdutorDialog(BaseController<Produtor> controller, int id, String label1, String label2, String label3) {
        super(controller, id, label1, label2, label3, "nascimento");
    }
}
