import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import stanford-corenlp-4.5.6-models;
import edu.stanford.nlp.simple.*;

public class GUI extends JFrame implements ActionListener {
    private JTextArea inputTextArea;
    private JButton analyzeButton;
    private JLabel resultLabel;

    public GUI() {
        setTitle("Sentiment Analysis GUI");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        inputTextArea = new JTextArea(20, 30);
        analyzeButton = new JButton("Analyze");
        resultLabel = new JLabel();

        // Set layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
        inputPanel.add(analyzeButton, BorderLayout.SOUTH);
        add(inputPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        // Add action listener to the analyze button
        analyzeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == analyzeButton) {
            String text = inputTextArea.getText();
            if (!text.isEmpty()) {
                // Perform sentiment analysis
                Document doc = new Document(text);
                String sentiment = doc.sentences().get(0).sentiment();
                resultLabel.setText("Sentiment: " + sentiment);
                StanfordCoreNLP pipeline = new StanfordCoreNLP();
        
                // Input sentence for NER
                
                // Annotate the text for Named Entity Recognition
                Annotation document = new Annotation(text);
                pipeline.annotate(document);
                
                // Retrieve and print the Named Entities
                for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String word = token.get(CoreAnnotations.TextAnnotation.class);
                        String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                        System.out.println(word + " : " + ne);
                    }
                }
            }
            else {
                    resultLabel.setText("Please enter some text.");
            }
        }
    }

    public static void main(String[] args) {
        // Set native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}