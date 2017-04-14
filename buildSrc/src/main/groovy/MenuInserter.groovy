/**
 * @file
 *
 * Created on 14.04.2017
 * by <a href="mailto:mhw@teambaltic.de">Mathias-H.&nbsp;Weber&nbsp;(MW)</a>
 *
 * Coole Software - Mein Beitrag im Kampf gegen die Klimaerw�rmung!
 *
 * Copyright (C) 2017 TeamBaltic
 */
//#############################################################################


//########################################################################
/**
 */
public class MenuInserter
{
    def m_HTMLFiles;
    File m_MenuFile;
    def m_Lines_menuFile

    public MenuInserter( File fHTMLFolder, File fMenuFile  )
    {
        m_HTMLFiles = []
        fHTMLFolder.eachFileMatch(~/.*\.html$/) { m_HTMLFiles << it }
        m_Lines_menuFile = fMenuFile.readLines()
    }

    void insertMenu()
    {
        m_HTMLFiles.each{
            println ("Verarbeite "+it) 
            insertMenu( it )
        }
    }
    
    void insertMenu( File fHTMLFile )
    {
//        println "Lese "+m_HTMLFile
        // Zerlegen in Zeilen vor und nach "<body ...>
        def lines_leading   = []
        def lines_trailing  = []
        def lines_html      = fHTMLFile.readLines()
        def hitBody         = false
        def menuFound       = false
        lines_html.each{ String line ->
            if( !hitBody ){
                lines_leading.add( line )
                if( line ==~ /<body class=".+">/ ){
                    hitBody = true
                }
            } else {
                if( line ==~ /<!-- START eingefügtes Menu -->/ ){
                    println "Menu schon vorhanden!"
                    menuFound = true
                    return false
                }
                if( !menuFound ){
                    lines_trailing.add( line )
                }
            }
        }
        if( !menuFound ){
            // Zusammenfügen aller Zeilen:
            def allLines = lines_leading + m_Lines_menuFile + lines_trailing
            saveFile( fHTMLFile, allLines )
        }
    }

    void saveFile( fHTMLFile, allLines )
    {
        //Save File
        println "Menu wird zu Datei hinzugefügt: "+fHTMLFile
        fHTMLFile.setText('')
        allLines.each{ String line ->
            fHTMLFile.append( line+"\n" )
        }
    }
}
