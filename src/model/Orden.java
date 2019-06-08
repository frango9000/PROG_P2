package src.model;

import src.dao.OrdenesDao;
import src.dao.ServidoDao;
import src.model.abstractmodel.AbstractOrden;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Orden extends AbstractOrden implements IPersistable {

    protected ArrayList<Servido> servidos = new ArrayList<>();

    public Orden() {
        super();
    }

    public Orden(LocalDateTime apertura, LocalDateTime cierre, float total) {
        super(apertura, cierre, total);
    }

    public Orden(int id, String dbOpen, String dbClose, float total) {
        super(id, dbOpen, dbClose, total);
        updateServidos();
    }

    public ArrayList<Servido> getServidos() {
        return servidos;
    }

    public void setServidos(ArrayList<Servido> servidos) {
        this.servidos = servidos;
    }

    public void addServido(Servido servidos) {
        this.servidos.add(servidos);
    }

    public void addServidos(ArrayList<Servido> servido) {
        servidos.addAll(servido);
    }

    @Override
    public int updateOnDb() {
        return OrdenesDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return OrdenesDao.getInstance().updateDao(this);
    }

    private void updateServidos() {
        servidos = ServidoDao.getInstance().getByIdOrden(this.id);
    }
}
