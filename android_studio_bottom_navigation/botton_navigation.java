public class MainActivity{
    /**
     * R.id.fragmentContainer       (可替换)-activity_main布局中的FragmentLayout控件，不必实例化，但是由new 的Fragment类替换
     * bottomNavigationView         (可替换)-activity_main布局中的BottomNavigationView控件，就是底部导航栏
     * ScanFragment()               (可替换)-创建的java class (反射scaner_fragment布局)
     * R.id.scan                    (可替换)-创建的menu局部中的一个item
     * UploadFragment()     
     * R.id.send           
     * DownloadFragment()
     * R.id.receive
     */
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //底部导航初始化
        bottomNavigationView = findViewById(R.id.navigation);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ScanFragment()).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.scan:
                        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                        fragment = new ScanFragment();
                        break;
                    case R.id.send:
                        Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                        fragment = new UploadFragment();
                        break;
                    case R.id.receive:
                        Toast.makeText(MainActivity.this,"3",Toast.LENGTH_SHORT).show();
                        fragment = new DownloadFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });
    }

}