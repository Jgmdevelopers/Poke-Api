package ar.com.jgmdevelopers.javapokeapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;


public class CPokeApi {
    public void MostrarPokemon(JTable tablaPokemon, JTextField buscador, JTextField nombre, JTextField peso, JTextField altura, JTextField experienciaBase, JLabel foto ) {
        
        DefaultTableModel modelo = new DefaultTableModel();
        String[] nombreColumnas = {"Nombre", "Peso", "Altura"};
        modelo.setColumnIdentifiers(nombreColumnas);
        
        tablaPokemon.setModel(modelo);
        
        try {
            
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/"+buscador.getText());
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); //ABRIMOS LA CONECCION DE LA API
            
            conn.setRequestMethod("GET"); // Metodo para obtener la info
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); //leer la info que nos envia la api
            StringBuilder response = new StringBuilder(); 
            String line;
            
            while ((line = reader.readLine()) != null ){                
            
                response.append(line);
            
            }
            
            reader.close();
            
            JSONObject jsonObject = new JSONObject(response.toString());
            String name = jsonObject.getString("name");
            int weight = jsonObject.getInt("weight");
            int height = jsonObject.getInt("height");
            int base_experience = jsonObject.getInt("base_experience");
            
            modelo.addRow(new Object[]{name, weight,height});
            
            nombre.setText(name);
            peso.setText(String.valueOf(weight));
            altura.setText(String.valueOf(height));
            experienciaBase.setText(String.valueOf(base_experience));
            
            String imageUrl = jsonObject.getJSONObject("sprites").getString("front_default");
            
            ImageIcon ico = new ImageIcon(new URL(imageUrl));
            foto.setIcon(ico);

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingrese solo id o nombre del pokemon existente, error: "+e.toString());
        }
    }
}
