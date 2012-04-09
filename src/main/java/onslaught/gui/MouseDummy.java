package onslaught.gui;

import java.awt.geom.Rectangle2D;
import onslaught.core.enums.MouseEnum;
import onslaught.core.interfaces.IMouseHandler;
import onslaught.game.GameOperations;
import onslaught.interfaces.ISelectable;
import onslaught.model.Entity;
import onslaught.model.enums.TurretEnum;
import onslaught.model.turret.Turret;

/**
 *
 * @author ethic
 */
public class MouseDummy extends Entity {

    private Turret turret = null;
    private CursorSprite defSprite;
    private GameOperations gameOps;
    private ISelectable selected = null;
    private Integer oldX;
    private Integer oldY;

    public MouseDummy(GameOperations gameOps) {
        super(new CursorSprite(), IMouseHandler.DEFAULT.getX(), IMouseHandler.DEFAULT.getY());
        defSprite = (CursorSprite) sprite;
        this.gameOps = gameOps;
    }

    public void setGrabbed(boolean grabbed) {
        if (!grabbed) {
            oldX = IMouseHandler.DEFAULT.getX();
            oldY = IMouseHandler.DEFAULT.getY();
        }
        if (oldX != null) {
            IMouseHandler.DEFAULT.setNewPosition(oldX, oldY);
        }
        IMouseHandler.DEFAULT.setGrabbed(grabbed);
    }

    @Override
    public void collidedWith(Entity other) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(long time) {
        x = IMouseHandler.DEFAULT.getX();
        y = IMouseHandler.DEFAULT.getY();

        if (IMouseHandler.DEFAULT.isKeydown(MouseEnum.LEFT_BUTTON)) {
            if (turret != null) {
                turret.setX(x);
                turret.setY(y);
                turret.setState(TurretEnum.OPERATING);
                gameOps.addEntity(turret);
                turret = null;
            } else {
                Rectangle2D rect = new Rectangle2D.Double(x - defSprite.widthPart, y - defSprite.heightPart, defSprite.getWidth(), defSprite.getHeight());
                if (selected != null) {
                    selected.deselect();
                    selected = null;
                }
                for (ISelectable _tur : gameOps.getSelectables()) {
                    if (_tur.getClickZone().intersects(rect)) {
                        _tur.select();
                        selected = _tur;
                        break;
                    }
                }
            }
        }
        if (turret == null) {
            setSprite(defSprite);
        } else {
            setSprite(turret.getSprite());
        }

    }

    @Override
    public void draw() {
        sprite.draw((float) x, (float) y, (float) z, 0);
    }

    public void setTurret(Turret turret) {
        this.turret = turret;
    }

    public ISelectable getSelected() {
        return selected;
    }
}
