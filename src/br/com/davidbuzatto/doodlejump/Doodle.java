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
public class Doodle {
    
    private static final double MOVE_SPEED = 400;
    private static final double JUMP_SPEED = -800;
    private static final double MAX_FALL_SPEED = 600;
    
    public Vector2 pos;
    public Vector2 dim;
    public Vector2 vel;
    public Color color;
    private Image image;
    
    public AABB aabb;
    
    public Doodle( Image image ) {
        this.pos = new Vector2();
        this.dim = new Vector2( image.getWidth(), image.getHeight() );
        this.vel = new Vector2();
        this.color = EngineFrame.BLUE;
        this.aabb = new AABB();
        this.image = image;
    }
    
    public void update( double delta, EngineFrame e ) {
        
        pos.x += vel.x * delta;
        pos.y += vel.y * delta;
        
        if ( pos.x < 0 ) {
            pos.x = 0;
        } else if ( pos.x + dim.x > e.getScreenWidth() ) {
            pos.x = e.getScreenWidth() - dim.x;
        }
        
        updateAABB();
        
        if ( e.isKeyDown( EngineFrame.KEY_RIGHT ) ) {
            vel.x = MOVE_SPEED;
        } else if ( e.isKeyDown( EngineFrame.KEY_LEFT ) ) {
            vel.x = -MOVE_SPEED;
        } else {
            vel.x = 0;
        }
        
        vel.y += Main.GRAVITY * delta;
        
        if ( vel.y > MAX_FALL_SPEED ) {
            vel.y = MAX_FALL_SPEED;
        }
        
    }
    
    public void draw( EngineFrame e ) {
        /*e.fillRectangle( pos, dim, color );
        e.drawAABB( aabb, EngineFrame.BLACK );*/
        e.drawImage( image, pos.x, pos.y );
    }
    
    private void updateAABB() {
        aabb.x1 = pos.x;
        aabb.y1 = pos.y;
        aabb.setSize( dim.x, dim.y );
    }
    
    public void jump( Platform p ) {
        pos.y = p.pos.y - dim.y;
        vel.y = JUMP_SPEED;
        updateAABB();
    }
    
}
