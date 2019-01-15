package edu.uwm.capstone;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import edu.uwm.capstone.db.address.AddressDao;
import edu.uwm.capstone.db.address.AddressDaoRowMapper;
import edu.uwm.capstone.db.certification.CertificationDao;
import edu.uwm.capstone.db.certification.CertificationDaoRowMapper;
import edu.uwm.capstone.db.company.CompanyDao;
import edu.uwm.capstone.db.company.CompanyDaoRowMapper;
import edu.uwm.capstone.db.document.DocumentDao;
import edu.uwm.capstone.db.document.DocumentDaoRowMapper;
import edu.uwm.capstone.db.education.EducationDao;
import edu.uwm.capstone.db.education.EducationDaoRowMapper;
import edu.uwm.capstone.db.project.ProjectDao;
import edu.uwm.capstone.db.project.ProjectDaoRowMapper;
import edu.uwm.capstone.db.contact.ContactDao;
import edu.uwm.capstone.db.contact.ContactDaoRowMapper;
import edu.uwm.capstone.db.ProfileDao;
import edu.uwm.capstone.db.ProfileDaoRowMapper;
import edu.uwm.capstone.db.position.PositionDao;
import edu.uwm.capstone.db.position.PositionDaoRowMapper;
import edu.uwm.capstone.db.user.UserDao;
import edu.uwm.capstone.db.user.UserDaoRowMapper;
import edu.uwm.capstone.sql.statement.ISqlStatementsFileLoader;
import edu.uwm.capstone.sql.statement.SqlStatementsFileLoader;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@ConfigurationProperties(prefix = "service")
@EnableWebSecurity
@EnableSwagger2
public class ApplicationConfig extends WebSecurityConfigurerAdapter{

    //@Value(value = "https://uwm-capstone.auth0")
    private String apiAudience = "https://uwm-capstone.auth0/";
    //@Value(value = "https://uwm-capstone.auth0.com/")
    private String issuer = "https://uwm-capstone.auth0.com/";

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    protected String dbDriverClassName;
    protected String dbDriverUrl;
    protected String dbUsername;
    protected String dbPassword;
    protected String dbMigrationLocation;
    protected int dbPoolMaxwait;
    protected boolean dbPoolRemoveabandoned;
    protected int dbPoolRemoveabandonedtimeout;
    protected boolean dbPoolLogabandoned;
    protected long dbPoolMaxage;
    protected String sqlStatementsResourceLocation;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8333", "http://localhost:4000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    // All requests to our API must be accompanied by a bearer token, otherwise 401 unauthorized
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .antMatchers("/certification/**", "/education/**", "/company/**", "/position/**", "/project/**","/user/**","/address/**","/contact/**")
                //.anyRequest()
                .authenticated();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        LOGGER.info("Loading DataSource");
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(dbDriverClassName);
        poolProperties.setUrl(dbDriverUrl);
        poolProperties.setUsername(dbUsername);
        poolProperties.setPassword(dbPassword);
        poolProperties.setTestOnBorrow(true);
        poolProperties.setValidationQuery("SELECT 1");

        // Set additional pool properties
        poolProperties.setMaxWait(dbPoolMaxwait);
        poolProperties.setRemoveAbandoned(dbPoolRemoveabandoned);
        poolProperties.setRemoveAbandonedTimeout(dbPoolRemoveabandonedtimeout);
        poolProperties.setLogAbandoned(dbPoolLogabandoned);
        poolProperties.setMaxAge(dbPoolMaxage);
        poolProperties.setMaxActive(600);


        DataSource ds = new DataSource();
        ds.setPoolProperties(poolProperties);

        LOGGER.info("Running database migration on {}", dbDriverUrl);
        Flyway flyway = new Flyway();
        flyway.setLocations(dbMigrationLocation.split("\\s*,\\s*"));
        flyway.setOutOfOrder(true);
        flyway.setDataSource(ds);
        flyway.repair();
        flyway.migrate();

        return ds;
    }

    @Bean
    public ISqlStatementsFileLoader sqlStatementsFileLoader() {
        SqlStatementsFileLoader loader = new SqlStatementsFileLoader();
        loader.setStatementResourceLocation(sqlStatementsResourceLocation);
        return loader;
    }

    @Bean
    public ProfileDao profileDao() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setDataSource(dataSource());
        profileDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        profileDao.setRowMapper(profileDaoRowMapper());
        return profileDao;
    }

    @Bean
    public ProfileDaoRowMapper profileDaoRowMapper() {
        return new ProfileDaoRowMapper();
    }

    @Bean
    public AddressDao addressDao() {
        AddressDao addressDao = new AddressDao();
        addressDao.setDataSource(dataSource());
        addressDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        addressDao.setRowMapper(addressDaoRowMapper());
        return addressDao;
    }

    @Bean
    public AddressDaoRowMapper addressDaoRowMapper() { return new AddressDaoRowMapper(); }

    @Bean
    public CertificationDao certificationDao() {
        CertificationDao certificationDao = new CertificationDao();
        certificationDao.setDataSource(dataSource());
        certificationDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        certificationDao.setRowMapper(certificationDaoRowMapper());
        return certificationDao;
    }

    @Bean
    public CertificationDaoRowMapper certificationDaoRowMapper() {
        return new CertificationDaoRowMapper();
    }

    @Bean
    public CompanyDao companyDao() {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setDataSource(dataSource());
        companyDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        companyDao.setRowMapper(companyDaoRowMapper());
        return companyDao;
    }

    @Bean
    public CompanyDaoRowMapper companyDaoRowMapper() {
        return new CompanyDaoRowMapper();
    }

    @Bean
    public DocumentDao documentDao() {
        DocumentDao documentDao = new DocumentDao();
        documentDao.setDataSource(dataSource());
        documentDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        documentDao.setRowMapper(documentDaoRowMapper());
        return documentDao;
    }

    @Bean DocumentDaoRowMapper documentDaoRowMapper(){
        return new DocumentDaoRowMapper();
    }

    @Bean
    public PositionDao positionDao() {
        PositionDao positionDao = new PositionDao();
        positionDao.setDataSource(dataSource());
        positionDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        positionDao.setRowMapper(positionDaoRowMapper());
        return positionDao;
    }

    @Bean
    public PositionDaoRowMapper positionDaoRowMapper() {
        return new PositionDaoRowMapper();
    }

    @Bean
    public EducationDao educationDao() {
        EducationDao educationDao = new EducationDao();
        educationDao.setDataSource(dataSource());
        educationDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        educationDao.setRowMapper(educationDaoRowMapper());
        return educationDao;
    }

    @Bean
    public EducationDaoRowMapper educationDaoRowMapper() {
        return new EducationDaoRowMapper();
    }

    @Bean
    public ProjectDao projectDao() {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setDataSource(dataSource());
        projectDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        projectDao.setRowMapper(projectDaoRowMapper());
        return projectDao;
    }

    @Bean
    public ProjectDaoRowMapper projectDaoRowMapper() {
        return new ProjectDaoRowMapper();
    }

    @Bean
    public ContactDao contactDao() {
        ContactDao contactDao = new ContactDao();
        contactDao.setDataSource(dataSource());
        contactDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        contactDao.setRowMapper(contactDaoRowMapper());
        return contactDao;
    }

    @Bean
    public ContactDaoRowMapper contactDaoRowMapper() { return new ContactDaoRowMapper(); }

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        userDao.setSqlStatementsFileLoader(sqlStatementsFileLoader());
        userDao.setRowMapper(UserDaoRowMapper());
        return userDao;
    }

    @Bean
    public UserDaoRowMapper UserDaoRowMapper() { return new UserDaoRowMapper(); }

    public void setDbDriverClassName(String dbDriverClassName) {
        this.dbDriverClassName = dbDriverClassName;
    }

    public String getDbDriverUrl() {
        return dbDriverUrl;
    }

    public void setDbDriverUrl(String dbDriverUrl) {
        this.dbDriverUrl = dbDriverUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbMigrationLocation() {
        return dbMigrationLocation;
    }

    public void setDbMigrationLocation(String dbMigrationLocation) {
        this.dbMigrationLocation = dbMigrationLocation;
    }

    public int getDbPoolMaxwait() {
        return dbPoolMaxwait;
    }

    public void setDbPoolMaxwait(int dbPoolMaxwait) {
        this.dbPoolMaxwait = dbPoolMaxwait;
    }

    public boolean isDbPoolRemoveabandoned() {
        return dbPoolRemoveabandoned;
    }

    public void setDbPoolRemoveabandoned(boolean dbPoolRemoveabandoned) {
        this.dbPoolRemoveabandoned = dbPoolRemoveabandoned;
    }

    public int getDbPoolRemoveabandonedtimeout() {
        return dbPoolRemoveabandonedtimeout;
    }

    public void setDbPoolRemoveabandonedtimeout(int dbPoolRemoveabandonedtimeout) {
        this.dbPoolRemoveabandonedtimeout = dbPoolRemoveabandonedtimeout;
    }

    public boolean isDbPoolLogabandoned() {
        return dbPoolLogabandoned;
    }

    public void setDbPoolLogabandoned(boolean dbPoolLogabandoned) {
        this.dbPoolLogabandoned = dbPoolLogabandoned;
    }

    public long getDbPoolMaxage() {
        return dbPoolMaxage;
    }

    public void setDbPoolMaxage(long dbPoolMaxage) {
        this.dbPoolMaxage = dbPoolMaxage;
    }

    public String getSqlStatementsResourceLocation() {
        return sqlStatementsResourceLocation;
    }

    public void setSqlStatementsResourceLocation(String sqlStatementsResourceLocation) {
        this.sqlStatementsResourceLocation = sqlStatementsResourceLocation;
    }
}
