package src.model;

import src.dao.MesasDao;
import src.dao.OrdenDao;
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

    public void refreshOrden() {
        if (idOrden > 0) {
            orden = OrdenDao.getInstance().get(idOrden);
        } else orden = null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int updateOnDb() {
        return MesasDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return MesasDao.getInstance().updateDao(this);
    }
}
