package ar.edu.utn.frsf.isi.dam.lab05;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] opcionesMenu;
    private DrawerLayout menuLateral;
    private ListView listaMenu;
    private AdapterView.OnItemClickListener clickMenuListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new JugadaFragment();
                    break;
                case 1:
                    fragment = new HistorialFragment();
                    break;
                case 2:
                    fragment = new AyudaFragment();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.contenido, fragment);
            fragmentTransaction.commit();
            // Highlight the selected item, update the title, and close the drawer
            listaMenu.setItemChecked(position, true);
            setTitle(opcionesMenu[position]);
            menuLateral.closeDrawer(listaMenu);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);
        listaMenu = (ListView) findViewById(R.id.left_drawer);
        opcionesMenu = getResources().getStringArray(R.array.menu_lateral);

        // Set the adapter for the list view
        listaMenu.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, opcionesMenu));
        // Set the list's click listener
        listaMenu.setOnItemClickListener(clickMenuListener);
    }


}
