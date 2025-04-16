/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package mtci;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Eugz
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showMenu method, of class Main.
     */
    @Test
    public void testShowMenu() {
        System.out.println("showMenu");
        Main instance = new Main();
        instance.showMenu();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of playGame method, of class Main.
     */
    @Test
    public void testPlayGame() {
        System.out.println("playGame");
        Main instance = new Main();
        instance.playGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showLeaderBoard method, of class Main.
     */
    @Test
    public void testShowLeaderBoard() {
        System.out.println("showLeaderBoard");
        Main instance = new Main();
        instance.showLeaderBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showAboutUs method, of class Main.
     */
    @Test
    public void testShowAboutUs() {
        System.out.println("showAboutUs");
        Main instance = new Main();
        instance.showAboutUs();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
