package br.com.davidbuzatto.doodlejump;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.math.MathUtils;

/**
 * DoodleJump game.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Main extends EngineFrame {
    
    public static final double GRAVITY = 2000;
    
    private Doodle doodle;
    private Platform[] platforms;
    private int startIndex;
    private int endIndex;
    
    private Image platformImage;
    
    private Camera2D camera;
    
    public Main() {
        super ( 480, 800, "Doodle Jump", 60, true );
    }
    
    @Override
    public void create() {
        
        doodle = new Doodle( loadImage( "resources/images/duke.png" ).resize( .3 ) );
        doodle.pos.x = getScreenWidth() / 2;
        doodle.pos.y = getScreenHeight() / 2;
        
        platforms = new Platform[100];
        platformImage = loadImage( "resources/images/platform.png" );
        camera = new Camera2D();
        
        startIndex = 0;
        endIndex = -1;
        
        int mapColums = 21;
        int mapLines = 40;
        
        buildMap( buildRandomMapData( mapLines, mapColums ) );
        
    }
    
    @Override
    public void update( double delta ) {
        doodle.update( delta, this );
        resolveDoodleCollision();
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );

        for ( int i = startIndex; i <= endIndex; i++ ) {
            platforms[i].draw( this );
        }
        
        doodle.draw( this );
    
    }
    
    private void buildMap( String mapData ) {
        
        double offset = 20;
        double y = getScreenHeight() - offset;
        String[] lines = mapData.split( "\n" );
        
        for ( int i = lines.length - 1; i >= 0; i-- ) {
            int k = 0;
            String line = lines[i];
            for ( char c : line.toCharArray() ) {
                if ( c == 'x' ) {
                    addPlatform( k * 20, y );
                }
                k++;
            }
            y -= offset;
        }
        
    }
    
    private String buildMapData( int lines, int columns ) {
        
        StringBuilder mapData = new StringBuilder();
        mapData.append( "x   x   x   x   x   x\n" );
        mapData.append( "\n".repeat( lines - 2 ) );
        mapData.append( "x   x   x   x   x   x" );
        
        return mapData.toString().trim();
        
    }
    
    private String buildRandomMapData( int lines, int columns ) {
        
        StringBuilder mapData = new StringBuilder();
        int vgap = 2;
        
        for ( int i = 0; i < lines; i++ ) {
            
            if ( MathUtils.getRandomValue( 0, 100 ) < 40 ) {
                int pos = MathUtils.getRandomValue( 0, columns+1 );
                if ( pos > 0 ) {
                    mapData.append( " ".repeat( pos-1 ) );
                }
                mapData.append( "x" );
                i += vgap;
                mapData.append( "\n".repeat( vgap ) );
            }
            
            mapData.append( "\n" );
            
        }
        
        return mapData.toString().trim();
        
    }
    
    private void addPlatform( double x, double y ) {
        
        Platform p = new Platform( x, y, platformImage );
        
        endIndex++;
        platforms[endIndex] = p;
        
    }
    
    private void resolveDoodleCollision() {
        
        if ( doodle.vel.y > 0 ) {
            for ( int i = startIndex; i <= endIndex; i++ ) {
                if ( CollisionUtils.checkCollisionAABBs( doodle.aabb, platforms[i].aabb ) &&
                     doodle.pos.y < platforms[i].pos.y - doodle.dim.y + 20 ) {
                    doodle.jump( platforms[i] );
                }
            }
        }
        
    }
    
    public static void main( String[] args ) {
        new Main();
    }
    
}
