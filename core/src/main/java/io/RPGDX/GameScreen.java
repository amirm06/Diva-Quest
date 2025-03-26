package io.RPGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw;

public class GameScreen extends ScreenAdapter {
    private Texture Diva;
    private Texture DivaRight1,DivaRight2;
    private Texture DivaLeft1,DivaLeft2;
    private Texture DivaDown,DivaDown1,DivaDown2;
    private Texture DivaUP,DivaUP1,DivaUP2;
    private SpriteBatch batch;
    private Viewport viewport;
    private Music music;
    private OrthographicCamera camera;
    private Sprite Divasprite;
    private Main game;
    private Texture backgroundTexture;
    private Rectangle Divarec;
    private boolean toggleFrame = false;
    private boolean isMovingRight = false;
    private int frameCounter=0;



    //constructor
    public GameScreen(Main game) {
        this.game = game;


    }

    public void show() {
        batch = new SpriteBatch();

        Diva = new Texture(Gdx.files.internal("DivaDefault-1.png.png"));
        DivaRight1 = new Texture(Gdx.files.internal("New Piskel-1.png.png"));
        DivaRight2 = new Texture(Gdx.files.internal("walkingRight2.png"));
        DivaLeft1 = new Texture(Gdx.files.internal("left1.png"));
        DivaLeft2 = new Texture(Gdx.files.internal("left2.png"));
        DivaDown = new Texture(Gdx.files.internal("DivaDefault-1.png.png"));
        DivaDown1 = new Texture(Gdx.files.internal("standingfacingRight.png"));
        DivaDown2 = new Texture(Gdx.files.internal("standingfacingLEFT.png"));
        DivaUP = new Texture(Gdx.files.internal("New Piskel-1.png (2).png"));
        DivaUP1 = new Texture(Gdx.files.internal("New Piskel-2.png (2).png"));
        DivaUP2 = new Texture(Gdx.files.internal("New Piskel-3.png.png"));

        backgroundTexture = new Texture(Gdx.files.internal("preview_x2.png"));

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);

        Divasprite = new Sprite(Diva);
        Divasprite.setPosition(100, 100); // Set an initial position
        Divarec = new Rectangle(Divasprite.getX(), Divasprite.getY(), Divasprite.getWidth(), Divasprite.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        Divasprite.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);


    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }
    public void render(float delta) {
        camera.update();

        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 2f;
        float delta = Gdx.graphics.getDeltaTime();

        //--------------------------------------------FOR KEYBOARD INPUT------------------------------------------------
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            Divasprite.translateX(speed);
            isMovingRight = true;
            frameCounter++;
            if (frameCounter % 10 == 0) { // Change every 10 frames
                toggleFrame = !toggleFrame;
                Divasprite.setTexture(toggleFrame ? DivaRight1 : DivaRight2);
            }


        Divasprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            Divasprite.translateX(-speed);
            Divasprite.translateX(-speed * delta);
            isMovingRight = true;
            frameCounter++;
            if (frameCounter % 10 == 0) { // Change every 10 frames
                toggleFrame = !toggleFrame;
                Divasprite.setTexture(toggleFrame ? DivaLeft1 : DivaLeft2);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

            Divasprite.translateY(+speed);
            Divasprite.translateY(+speed * delta);
            isMovingRight = true;
            frameCounter++;
            if (frameCounter % 10 == 0) { // Change every 10 frames
                toggleFrame = !toggleFrame;
                Divasprite.setTexture(toggleFrame ? DivaUP1 : DivaUP2);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            Divasprite.translateY(-speed);
            Divasprite.translateY(-speed * delta);
            isMovingRight = true;
            frameCounter++;
            if (frameCounter % 10 == 0) { // Change every 10 frames
                toggleFrame = !toggleFrame;
                Divasprite.setTexture(toggleFrame ? DivaDown1 : DivaDown2);
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Divasprite.translateX(speed);
            Divasprite.translateX(speed * delta); // Move the bucket right
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Divasprite.translateX(-speed);
            Divasprite.translateX(-speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            Divasprite.translateY(+speed);
            Divasprite.translateY(+speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            Divasprite.translateY(-speed);
            Divasprite.translateY(-speed * delta);
        }

    }//--------------------------------------------------------------------------------------------------

    //-------------LOGIC
    private void logic() {

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        // Store the bucket size for brevity
        float bucketWidth = Divasprite.getWidth();
        float bucketHeight = Divasprite.getHeight();
        // Subtract the bucket width
        Divasprite.setX(MathUtils.clamp(Divasprite.getX(), 0, worldWidth - bucketWidth));
        // FIREBOLTS LOGIC
        float delta = Gdx.graphics.getDeltaTime();
        Divarec.set(Divasprite.getX(), Divasprite.getY(), bucketWidth, bucketHeight);

    }

    //THATS FOR DRAWING-----------------------------------------------------------------------------------------
    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin(); ///beggining of drawing
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        Divasprite.draw(batch);
        batch.end();

        System.out.println("works");


    }
}
