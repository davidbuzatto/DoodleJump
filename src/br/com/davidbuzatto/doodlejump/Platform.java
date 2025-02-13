package br.com.davidbuzatto.doodlejump;

import br.com.davidbuzatto.jsge.collision.aabb.AABB;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Platform {
    
    public Vector2 pos;
    public Vector2 dim;
    public Color color;
    private Image image;
    
    public AABB aabb;
    
    public Platform( double x, double y, Image image ) {
        this.pos = new Vector2( x, y );
        this.dim = new Vector2( image.getWidth(), image.getHeight() );
        this.color = EngineFrame.ORANGE;
        this.image = image;
        this.aabb = new AABB();
        updateAABB();
    }
    
    public void draw( EngineFrame e ) {
        e.drawImage( image, pos.x, pos.y );
        /*e.fillRectangle( pos, dim, color );
        e.drawAABB( aabb, EngineFrame.BLACK );*/
    }
    
    private void updateAABB() {
        aabb.x1 = pos.x;
        aabb.y1 = pos.y;
        aabb.setSize( dim.x, dim.y );
    }
    
}
