package src.model;

import src.dao.MesasDao;
import src.dao.OrdenesDao;
import src.model.abstractmodel.AbstractMesa;

public class Mesa extends AbstractMesa implements IPersistable {

    public Mesa(String mesa, int capacidad, int idOrden) {
        super(mesa, capacidad, idOrden);
        refreshOrden();
    }

    public Mesa(int idMesa, String mesa, int capacidad, int idOrden) {
        super(idMesa, mesa, capacidad, idOrden);
        refreshOrden();
    }

    @Override
    public void setIdOrden(int idOrden) {
        super.setIdOrden(idOrden);
        refreshOrden();
    }

    @Override
    public int updateOnDb() {
        return MesasDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return MesasDao.getInstance().updateDao(this);
    }

    private void refreshOrden() {
        if (idOrden > 0) {
            orden = OrdenesDao.getInstance().get(idOrden);
        } else {
            orden = null;
        }
    }
}
