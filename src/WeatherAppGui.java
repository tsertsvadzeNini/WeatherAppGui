import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame{

    private JSONObject weatherData;
    public WeatherAppGui(){
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 600);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        //search field
        JTextField searchTextField = new JTextField();

        searchTextField.setBounds(15,15,351,45);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);



        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        //Temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);


        // weather condition description
        JLabel weatherConditionDesc = new JLabel( "Cloudy");
        weatherConditionDesc.setBounds (0, 405,450, 36) ;
        weatherConditionDesc.setFont (new Font ("Dialog", Font. PLAIN,  32)) ;
        weatherConditionDesc.setHorizontalAlignment (SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,480,74,66);
        add(humidityImage);

        //humidity txt
        JLabel humidityText = new JLabel("<html><b>Humidity</b>100%</html>");
        humidityText.setBounds(90, 480,85,55);
        humidityImage.setFont(new Font ("Dialog", Font.PLAIN, 16));
        add(humidityText);

        //wind
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(210,480,74,66);
        add(windSpeedImage);

        //wind txt
        JLabel windText = new JLabel("<html><b>WindSpeed</b>15km</html>");
        windText.setBounds(290, 480,130,55);
        windText.setFont(new Font ("Dialog", Font.PLAIN, 16));
        add(windText);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        //change cursor when hovering over this button

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get location from user
                String userInput = searchTextField.getText();

                //validate inut - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }
                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update gui

                //update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch (weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                //update temperature txt
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "C");

                //update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                //update humidity
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b>" + humidity + "%</html>");

                //update wind speed text
                // update windspeed in action listener
                double windspeed = (double) weatherData.get("windspeed");
                windText.setText("<html><b>WindSpeed</b> " + windspeed + " km/h</html>");

                // set cursor for search button
                searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        add(searchButton);
    }



    //method to create images
    private ImageIcon loadImage(String resourcePAth){
        try{
            BufferedImage image = ImageIO.read(new File(resourcePAth));

            return new ImageIcon(image);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Image not found");
        return null;
    }
}
