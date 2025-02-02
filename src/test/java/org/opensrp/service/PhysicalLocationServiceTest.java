package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.opensrp.domain.StructureCount.STRUCTURE_COUNT;
import static org.smartregister.domain.LocationProperty.PropertyStatus.ACTIVE;
import static org.smartregister.domain.LocationProperty.PropertyStatus.PENDING_REVIEW;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.opensrp.api.domain.Location;
import org.opensrp.api.util.LocationTree;
import org.opensrp.api.util.TreeNode;
import org.opensrp.domain.StructureCount;
import org.smartregister.domain.Geometry.GeometryType;
import org.opensrp.domain.LocationDetail;
import org.smartregister.domain.LocationProperty;
import org.smartregister.domain.LocationProperty.PropertyStatus;
import org.smartregister.domain.PhysicalLocation;
import org.opensrp.domain.StructureDetails;
import org.opensrp.repository.LocationRepository;
import org.opensrp.search.LocationSearchBean;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.JsonArray;
import org.smartregister.utils.PropertiesConverter;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({ "com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*", "org.w3c.*" })
public class PhysicalLocationServiceTest {
	
	private PhysicalLocationService locationService;
	
	private LocationRepository locationRepository;
	
	private ArgumentCaptor<PhysicalLocation> argumentCaptor = ArgumentCaptor.forClass(PhysicalLocation.class);
	
	public static Gson gson = new GsonBuilder().registerTypeAdapter(LocationProperty.class, new PropertiesConverter())
	        .setDateFormat("yyyy-MM-dd'T'HHmm").create();
	
	public static String structureJson = "{\"type\":\"Feature\",\"id\":\"90397\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[32.5978597,-14.1699446],[32.5978956,-14.1699609],[32.5978794,-14.1699947],[32.5978434,-14.1699784],[32.5978597,-14.1699446]]]},\"properties\":{\"uid\":\"41587456-b7c8-4c4e-b433-23a786f742fc\",\"code\":\"21384443\",\"type\":\"Residential Structure\",\"status\":\"Active\",\"parentId\":\"3734\",\"geographicLevel\":5,\"effectiveStartDate\":\"2017-01-10T0000\",\"version\":0}}";
	
	public static String parentJson = "{\"type\":\"Feature\",\"id\":\"3734\",\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":[[[[32.59989007736522,-14.167432040756012],[32.599899215376524,-14.167521429770147],[32.59990104639621,-14.167611244163538],[32.599895558733124,-14.16770091736339],[32.59989008366051,-14.167739053114758],[32.59993602462787,-14.16775581827636],[32.600019423257834,-14.167793873088726],[32.60009945932262,-14.167838272618155],[32.60017562650404,-14.167888735376776],[32.60024744456398,-14.16794494480331],[32.60031445844555,-14.168006542967532],[32.60037624546743,-14.168073141362354],[32.60043241352514,-14.168144319105009],[32.6004826082858,-14.168219626534494],[32.60052651318807,-14.168298587010156],[32.60056384854283,-14.168380700508862],[32.60059438052627,-14.168465449920518],[32.600617914884936,-14.168552297450503],[32.60062693688366,-14.16860096246438],[32.600629671722004,-14.168608553641775],[32.600653206080665,-14.16869540117176],[32.60066959442628,-14.168783799132996],[32.600678733336906,-14.168873188147074],[32.60068056435659,-14.168963002540465],[32.600675075794186,-14.169052674840998],[32.60066230362247,-14.169141638475764],[32.60064232698187,-14.169229331368513],[32.60061527357607,-14.169315197738115],[32.60058131337695,-14.169398695293522],[32.6005406613225,-14.169479297031899],[32.60049357461884,-14.169556492138383],[32.600440351840795,-14.169629793180375],[32.60038132753629,-14.169698736107762],[32.60031687672256,-14.169762885648595],[32.60024740499381,-14.169821836208712],[32.60017335391722,-14.169875214569458],[32.600095189341566,-14.169922684384344],[32.600073628095515,-14.169933562583822],[32.60007671007219,-14.169963706060116],[32.600078541091875,-14.170053520453562],[32.60007305252941,-14.170143193653415],[32.600060280357695,-14.170232157288183],[32.600040303717094,-14.170319849281555],[32.60001324941197,-14.170405715651212],[32.599979289212854,-14.170489214105885],[32.59993897080693,-14.170569153943234],[32.599943946755786,-14.170617831547645],[32.599945777775474,-14.170707646840356],[32.59994028921301,-14.170797319140943],[32.59992751704135,-14.170886282775712],[32.59990754040069,-14.170973974769083],[32.59988048609562,-14.171059841138685],[32.5998465258965,-14.171143339593414],[32.59980587384205,-14.17122394043247],[32.59975878713834,-14.171301135538954],[32.59970556346104,-14.171374436580948],[32.59964653915648,-14.171443379508332],[32.59958208744342,-14.171507529049165],[32.59951261571467,-14.171566479609282],[32.59943856373877,-14.171619858869349],[32.59936039916312,-14.171667327784915],[32.599278615715605,-14.17170858778104],[32.59919373140565,-14.17174337715511],[32.599106283128485,-14.171771477371749],[32.59901682216866,-14.171792708566613],[32.59892591599828,-14.171806937640042],[32.5988341383848,-14.171814074659776],[32.59874206939105,-14.171814074659776],[32.598650291777574,-14.171806937640042],[32.59855938470787,-14.171792708566613],[32.59846992464736,-14.171771477371749],[32.59838247547094,-14.17174337715511],[32.5983273353383,-14.17172077809141],[32.59831050992216,-14.17173505572822],[32.59823645704688,-14.17178843408891],[32.59815829247128,-14.171835903903796],[32.59807650992303,-14.171877163899921],[32.597991625613076,-14.17191195327399],[32.59790417643665,-14.17194005259131],[32.597814715476765,-14.171961283786173],[32.59772380930639,-14.171975513758923],[32.59763203169297,-14.171982650778657],[32.59753996269916,-14.171982650778657],[32.59744818508574,-14.171975513758923],[32.597357278016034,-14.171961283786173],[32.59726781795547,-14.17194005259131],[32.59718036877905,-14.17191195327399],[32.597125046084045,-14.171889279566528],[32.59711651511515,-14.171899244054828],[32.59705206340203,-14.171963393595718],[32.59698259167334,-14.172022344155835],[32.59690853879806,-14.172075722516524],[32.59683037422246,-14.172123192331412],[32.596748590774894,-14.172164452327593],[32.596669124880236,-14.172197021275451],[32.59666535402294,-14.172199310949395],[32.596583571474696,-14.172240570945519],[32.59649868716474,-14.172275360319588],[32.59641123798832,-14.172303459636908],[32.596321777028436,-14.172324691731092],[32.59623087085805,-14.172338920804519],[32.59613909234531,-14.172346057824257],[32.5960470233515,-14.172346057824257],[32.595955245738025,-14.172338920804519],[32.59586433866832,-14.172324691731092],[32.595803283694636,-14.172310200954938],[32.59573533092083,-14.172367862786585],[32.59566127804561,-14.172421241147333],[32.59558311346996,-14.172468710062901],[32.59551500691208,-14.172503069560946],[32.59545514174147,-14.172546221730727],[32.59537697626649,-14.172593691545558],[32.59529519371824,-14.172634951541738],[32.59521030850897,-14.17266974091575],[32.59512285933255,-14.17269784023307],[32.59503339927198,-14.172719072327254],[32.59494249220228,-14.172733301400683],[32.59485071368954,-14.172740438420476],[32.59475864469573,-14.172740438420476],[32.59466686618299,-14.172733301400683],[32.594575960012605,-14.172719072327254],[32.59448649905272,-14.17269784023307],[32.59445585105675,-14.172687992656677],[32.59442677507565,-14.172690253552332],[32.59433470518257,-14.172690253552332],[32.5942429275691,-14.17268311653254],[32.59415202049939,-14.172668887459167],[32.594062559539566,-14.172647656264303],[32.59397511036309,-14.172619556946927],[32.593890226053134,-14.172584766673594],[32.59380844260562,-14.17254350667747],[32.593730278029966,-14.172496037761903],[32.593656225154746,-14.172442658501836],[32.593586753425996,-14.17238370794172],[32.593567544806376,-14.172364589254355],[32.59351203505241,-14.172317486362884],[32.593447582440035,-14.172253336821994],[32.59338855813547,-14.17218439389461],[32.593335334458175,-14.172111092852617],[32.59328824685514,-14.172033897746132],[32.59324759480069,-14.171953296907077],[32.593213634601625,-14.171869799351725],[32.5931865802965,-14.171783932082747],[32.5931666036559,-14.171696240089375],[32.59315383148419,-14.171607276454608],[32.59314834292172,-14.171517604154076],[32.5931501739414,-14.171427788861308],[32.59315931285203,-14.17133840074655],[32.59317570119771,-14.17125000278537],[32.59319923555631,-14.171163155255385],[32.59322976753981,-14.17107840584373],[32.593267104693155,-14.170996292344967],[32.59331100869616,-14.170917331869305],[32.59336120435614,-14.170842024439821],[32.59341737331317,-14.170770846697167],[32.59347916123437,-14.170704248302341],[32.59354617601525,-14.17064265013812],[32.593617994075196,-14.170586440711643],[32.593694163055204,-14.170535977952964],[32.59377420001937,-14.170491578423535],[32.593857599548635,-14.170453523611172],[32.59394383374098,-14.170422054534129],[32.594032357607254,-14.170397369942634],[32.594122611769194,-14.170379625419342],[32.594214026056534,-14.170368934278827],[32.5943060213059,-14.170365362171708],[32.59439801655525,-14.170368934278827],[32.594402085987554,-14.170369410020214],[32.59439156302028,-14.170336010998085],[32.594371587279,-14.170248319004656],[32.594358814207965,-14.17015935536989],[32.59435618369099,-14.170116383963887],[32.594347703084054,-14.170089466355762],[32.59432772734277,-14.170001773463014],[32.594314954271795,-14.169912809828245],[32.59430946660865,-14.169823137527715],[32.59431129672901,-14.169733323134324],[32.594320435639645,-14.169643934120245],[32.594336823985316,-14.16955553615901],[32.59436035834392,-14.169468688629022],[32.59439089032736,-14.169383940116688],[32.59442822658144,-14.16930182571866],[32.59447213148371,-14.169222865243],[32.59452232624443,-14.169147557813517],[32.59457849520146,-14.169076380070862],[32.59464028132396,-14.169009781676039],[32.5947072961049,-14.168948183511816],[32.59477911416485,-14.168891974984604],[32.59485528224553,-14.168841511326661],[32.59493531831032,-14.168797111797232],[32.595018716940274,-14.168759056984868],[32.59510495113261,-14.168727587907824],[32.595193474099574,-14.168702903316273],[32.5952837282615,-14.168685158792982],[32.59537514075021,-14.168674467652522],[32.59545842696491,-14.168671234589736],[32.5954623282239,-14.168656837343121],[32.5954928602074,-14.168572088830786],[32.595530195562105,-14.168489974432703],[32.59557410046443,-14.168411013957098],[32.595624295225086,-14.168335707426932],[32.595680463282804,-14.168264529684278],[32.59574225030468,-14.168197930390136],[32.595809264186244,-14.168136332225913],[32.59588108224619,-14.1680801236987],[32.59595725032687,-14.168029660040759],[32.596033266422175,-14.16798749083],[32.59606430112666,-14.167931676205853],[32.59611449588732,-14.16785636877637],[32.59617066394503,-14.167785191033715],[32.59623245006759,-14.167718592638892],[32.59629946394915,-14.167656993575347],[32.596371282009095,-14.167600785048137],[32.596447450089784,-14.167550322289514],[32.59652748525531,-14.167505922760085],[32.59661088388526,-14.167467867947721],[32.59669711717828,-14.167436398870677],[32.59678564014524,-14.167411714279126],[32.59687589340779,-14.167393969755835],[32.5969673058965,-14.167383278615375],[32.59705930024654,-14.1673797065082],[32.597151294596635,-14.167383278615375],[32.59724270708534,-14.167393969755835],[32.5973329603479,-14.167411714279126],[32.59742148331486,-14.167436398870677],[32.59746305357709,-14.16745156953425],[32.59747073828402,-14.167423210312847],[32.59750126936814,-14.167338461800512],[32.597508451353974,-14.167322665208815],[32.59752348801862,-14.167280928571927],[32.59752577679319,-14.16727248213931],[32.59755630787737,-14.167187732727655],[32.59759364413145,-14.16710561832957],[32.5976375481344,-14.16702665875323],[32.59768774289506,-14.166951351323744],[32.59774391095277,-14.16688017358109],[32.59780569707533,-14.166813574287005],[32.59787271005757,-14.166751976122782],[32.59794452811752,-14.16669576759557],[32.598020695298885,-14.166645303937571],[32.59810073136373,-14.166600904408142],[32.59818412909431,-14.166562850495096],[32.598270361488055,-14.166531381418054],[32.598358884455024,-14.166506696826557],[32.59844913681826,-14.166488952303268],[32.59852580672065,-14.166479984263844],[32.598559981857704,-14.166473265428806],[32.598651394346405,-14.166462574288346],[32.598743387797185,-14.166459002181169],[32.59883538214722,-14.166462574288346],[32.59892679373661,-14.166473265428806],[32.599017046999165,-14.166491009952097],[32.59910556996613,-14.166515694543648],[32.599191802359826,-14.166547163620635],[32.59927520009046,-14.166585218433056],[32.599355236155304,-14.166629617962426],[32.599431403336666,-14.166680081620425],[32.5995032204973,-14.16673629014764],[32.59957023437886,-14.166797888311862],[32.59963202050142,-14.16686448670663],[32.59968818855913,-14.166935664449284],[32.59973838331979,-14.167010971878824],[32.59978228732274,-14.167089932354429],[32.5998196226775,-14.167172046752512],[32.59985015466094,-14.167256795264848],[32.5998736890196,-14.167343642794833],[32.59989007736522,-14.167432040756012]]]]},\"properties\":{\"uid\":\"41587456-b7c8-4c4e-b433-23a786f742fc\",\"code\":\"3734\",\"type\":\"Intervention Unit\",\"status\":\"Active\",\"parentId\":\"21\",\"name\":\"01_5\",\"geographicLevel\":4,\"effectiveStartDate\":\"2015-01-01T0000\",\"version\":0}}";
	
	@Before
	public void setUp() {
		locationRepository = mock(LocationRepository.class);
		locationService = new PhysicalLocationService();
		locationService.setLocationRepository(locationRepository);
	}
	
	@Test
	public void testGetLocation() {
		when(locationRepository.get("3734", true, false)).thenReturn(createLocation());
		PhysicalLocation parentLocation = locationService.getLocation("3734", true, false);
		verify(locationRepository).get("3734", true, false);
		verifyLocationData(parentLocation);
		
	}
	
	@Test
	public void testGetLocationWithVersion() {
		when(locationRepository.get("3734", true, 0)).thenReturn(createLocation());
		PhysicalLocation parentLocation = locationService.getLocation("3734", true, 0);
		verify(locationRepository).get("3734", true, 0);
		verifyLocationData(parentLocation);
	}
	
	private void verifyLocationData(PhysicalLocation parentLocation) {
		verifyNoMoreInteractions(locationRepository);
		assertEquals("3734", parentLocation.getId());
		assertEquals("Feature", parentLocation.getType());
		assertEquals(GeometryType.MULTI_POLYGON, parentLocation.getGeometry().getType());
		
		assertEquals("21", parentLocation.getProperties().getParentId());
		assertEquals("01_5", parentLocation.getProperties().getName());
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", parentLocation.getProperties().getUid());
		assertEquals("3734", parentLocation.getProperties().getCode());
		
		assertFalse(parentLocation.getGeometry().getCoordinates().isJsonNull());
		JsonArray coordinates = parentLocation.getGeometry().getCoordinates().get(0).getAsJsonArray().get(0)
		        .getAsJsonArray();
		assertEquals(267, coordinates.size());
		
		JsonArray coordinate1 = coordinates.get(0).getAsJsonArray();
		assertEquals(32.59989007736522, coordinate1.get(0).getAsDouble(), 0);
		assertEquals(-14.167432040756012, coordinate1.get(1).getAsDouble(), 0);
		
		JsonArray coordinate67 = coordinates.get(66).getAsJsonArray();
		assertEquals(32.5988341383848, coordinate67.get(0).getAsDouble(), 0);
		assertEquals(-14.171814074659776, coordinate67.get(1).getAsDouble(), 0);
	}
	
	@Test
	public void testGetStructure() throws ParseException {
		
		when(locationRepository.getStructure("90397", true)).thenReturn(createStructure());
		PhysicalLocation structure = locationService.getStructure("90397", true);
		verify(locationRepository).getStructure("90397", true);
		verifyNoMoreInteractions(locationRepository);
		
		assertEquals("Feature", structure.getType());
		assertEquals("90397", structure.getId());
		
		assertEquals(GeometryType.POLYGON, structure.getGeometry().getType());
		assertFalse(structure.getGeometry().getCoordinates().isJsonNull());
		
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", structure.getProperties().getUid());
		assertEquals("21384443", structure.getProperties().getCode());
		assertEquals("Residential Structure", structure.getProperties().getType());
		assertEquals(PropertyStatus.ACTIVE, structure.getProperties().getStatus());
		assertEquals("3734", structure.getProperties().getParentId());
		assertNull(structure.getProperties().getName());
		assertEquals(5, structure.getProperties().getGeographicLevel());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-10"),
		    structure.getProperties().getEffectiveStartDate());
		assertNull(structure.getProperties().getEffectiveEndDate());
		assertEquals(0, structure.getProperties().getVersion());
	}
	
	@Test
	public void testGetAllLocations() {
		List<PhysicalLocation> expected = new ArrayList<>();
		expected.add(createLocation());
		when(locationRepository.getAll()).thenReturn(expected);
		List<PhysicalLocation> locations = locationService.getAllLocations();
		verify(locationRepository).getAll();
		verifyNoMoreInteractions(locationRepository);
		
		assertEquals(1, locations.size());
		PhysicalLocation parentLocation = locations.get(0);
		assertEquals("3734", parentLocation.getId());
		assertEquals("Feature", parentLocation.getType());
		assertEquals(GeometryType.MULTI_POLYGON, parentLocation.getGeometry().getType());
		
		assertEquals("21", parentLocation.getProperties().getParentId());
		assertEquals("01_5", parentLocation.getProperties().getName());
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", parentLocation.getProperties().getUid());
		assertEquals("3734", parentLocation.getProperties().getCode());
	}
	
	@Test
	public void testAddOrUpdateShouldAddLocation() {
		when(locationRepository.get("3734")).thenReturn(null);
		locationService.addOrUpdate(createLocation());
		verify(locationRepository).add(argumentCaptor.capture());
		
		PhysicalLocation parentLocation = argumentCaptor.getValue();
		assertEquals("3734", parentLocation.getId());
		assertEquals("Feature", parentLocation.getType());
		assertEquals(GeometryType.MULTI_POLYGON, parentLocation.getGeometry().getType());
		
		assertEquals("21", parentLocation.getProperties().getParentId());
		assertEquals("01_5", parentLocation.getProperties().getName());
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", parentLocation.getProperties().getUid());
		assertEquals("3734", parentLocation.getProperties().getCode());
		
	}
	
	@Test
	public void testAddOrUpdateShouldUpdateLocation() {
		PhysicalLocation physicalLocation = createLocation();
		when(locationRepository.get("3734", true, false)).thenReturn(physicalLocation);
		when(locationRepository.findLocationByIdentifierAndStatus("3734",Arrays.asList(ACTIVE.name(), PENDING_REVIEW.name()), true)).thenReturn(physicalLocation);
		locationService.addOrUpdate(createLocation());
		verify(locationRepository).update(argumentCaptor.capture());
		
		PhysicalLocation parentLocation = argumentCaptor.getValue();
		assertEquals("3734", parentLocation.getId());
		assertEquals("Feature", parentLocation.getType());
		assertEquals(GeometryType.MULTI_POLYGON, parentLocation.getGeometry().getType());
		
		assertEquals("21", parentLocation.getProperties().getParentId());
		assertEquals("01_5", parentLocation.getProperties().getName());
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", parentLocation.getProperties().getUid());
		assertEquals("3734", parentLocation.getProperties().getCode());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddOrUpdateWithNullId() {
		PhysicalLocation physicalLocation = createLocation();
		physicalLocation.setId(null);
		locationService.addOrUpdate(physicalLocation);
		
	}
	
	@Test
	public void testAddOrUpdateShouldAddStructure() throws ParseException {
		when(locationRepository.getStructure("90397", true)).thenReturn(null);
		locationService.addOrUpdate(createStructure());
		verify(locationRepository).add(argumentCaptor.capture());
		
		PhysicalLocation structure = argumentCaptor.getValue();
		
		assertEquals(GeometryType.POLYGON, structure.getGeometry().getType());
		assertFalse(structure.getGeometry().getCoordinates().isJsonNull());
		
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", structure.getProperties().getUid());
		assertEquals("21384443", structure.getProperties().getCode());
		assertEquals("Residential Structure", structure.getProperties().getType());
		assertEquals(PropertyStatus.ACTIVE, structure.getProperties().getStatus());
		assertEquals("3734", structure.getProperties().getParentId());
		assertNull(structure.getProperties().getName());
		assertEquals(5, structure.getProperties().getGeographicLevel());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-10"),
		    structure.getProperties().getEffectiveStartDate());
		assertNull(structure.getProperties().getEffectiveEndDate());
		assertEquals(0, structure.getProperties().getVersion());
	}
	
	@Test
	public void testAddOrUpdateShouldUpdateStructure() throws ParseException {
		PhysicalLocation physicalLocation = createStructure();
		when(locationRepository.getStructure("90397", true)).thenReturn(physicalLocation);
		locationService.addOrUpdate(physicalLocation);
		verify(locationRepository).update(argumentCaptor.capture());
		
		PhysicalLocation structure = argumentCaptor.getValue();
		
		assertEquals(GeometryType.POLYGON, structure.getGeometry().getType());
		assertFalse(structure.getGeometry().getCoordinates().isJsonNull());
		
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", structure.getProperties().getUid());
		assertEquals("21384443", structure.getProperties().getCode());
		assertEquals("Residential Structure", structure.getProperties().getType());
		assertEquals(PropertyStatus.ACTIVE, structure.getProperties().getStatus());
		assertEquals("3734", structure.getProperties().getParentId());
		assertNull(structure.getProperties().getName());
		assertEquals(5, structure.getProperties().getGeographicLevel());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-10"),
		    structure.getProperties().getEffectiveStartDate());
		assertNull(structure.getProperties().getEffectiveEndDate());
		assertEquals(0, structure.getProperties().getVersion());
		
	}
	
	@Test
	public void testAdd() {
		PhysicalLocation expected = createStructure();
		locationService.add(expected);
		verify(locationRepository).add(argumentCaptor.capture());
		
		PhysicalLocation structure = argumentCaptor.getValue();
		
		assertEquals(GeometryType.POLYGON, structure.getGeometry().getType());
		assertFalse(structure.getGeometry().getCoordinates().isJsonNull());
		
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", structure.getProperties().getUid());
		assertEquals("21384443", structure.getProperties().getCode());
		assertEquals("Residential Structure", structure.getProperties().getType());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddWithNullId() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		locationService.add(physicalLocation);
	}
	
	@Test
	public void testUpdate() {
		PhysicalLocation expected = createLocation();
		locationService.update(expected);
		verify(locationRepository).update(argumentCaptor.capture());
		
		PhysicalLocation parentLocation = argumentCaptor.getValue();
		assertEquals("3734", parentLocation.getId());
		assertEquals("Feature", parentLocation.getType());
		assertEquals(GeometryType.MULTI_POLYGON, parentLocation.getGeometry().getType());
		
		assertEquals("21", parentLocation.getProperties().getParentId());
		assertEquals("01_5", parentLocation.getProperties().getName());
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", parentLocation.getProperties().getUid());
		assertEquals("3734", parentLocation.getProperties().getCode());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateWithNullId() {
		PhysicalLocation physicalLocation = new PhysicalLocation();
		locationService.update(physicalLocation);
	}
	
	@Test
	public void testFindLocationsByServerVersion() {
		List<PhysicalLocation> expected = new ArrayList<>();
		expected.add(createLocation());
		when(locationRepository.findLocationsByServerVersion(123l)).thenReturn(expected);
		
		List<PhysicalLocation> locations = locationService.findLocationsByServerVersion(123l);
		verify(locationRepository).findLocationsByServerVersion(123l);
		verifyNoMoreInteractions(locationRepository);
		
		assertEquals(1, locations.size());
		PhysicalLocation parentLocation = locations.get(0);
		assertEquals("3734", parentLocation.getId());
		assertEquals("Feature", parentLocation.getType());
		assertEquals(GeometryType.MULTI_POLYGON, parentLocation.getGeometry().getType());
		
		assertEquals("21", parentLocation.getProperties().getParentId());
		assertEquals("01_5", parentLocation.getProperties().getName());
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", parentLocation.getProperties().getUid());
		assertEquals("3734", parentLocation.getProperties().getCode());
		
	}
	
	@Test
	public void testFindLocationsByNames() {
		String locationNames = "01_5";
		List<PhysicalLocation> expected = new ArrayList<>();
		List<PhysicalLocation> locations;
		
		locations = locationService.findLocationsByNames(locationNames, 0l);
		assertEquals(0, locations.size());
		
		expected.add(createLocation());
		when(locationService.findLocationsByNames(locationNames, 0l)).thenReturn(expected);
		locations = locationService.findLocationsByNames(locationNames, 0l);
		assertEquals(1, locations.size());
		PhysicalLocation location = locations.get(0);
		assertEquals("01_5", location.getProperties().getName());
		assertEquals("Feature", location.getType());
		assertEquals("3734", location.getId());
		assertEquals(GeometryType.MULTI_POLYGON, location.getGeometry().getType());
		
		//		search with more than one name
		locationNames = "01_5,other_location_name";
		when(locationService.findLocationsByNames(locationNames, 0l)).thenReturn(expected);
		locations = locationService.findLocationsByNames(locationNames, 0l);
		assertEquals(1, locations.size());
		location = locations.get(0);
		assertEquals("01_5", location.getProperties().getName());
		assertEquals("Feature", location.getType());
		assertEquals("3734", location.getId());
		assertEquals(GeometryType.MULTI_POLYGON, location.getGeometry().getType());
		
	}
	
	@Test
	public void testFindStructuresByParentAndServerVersion() {
		
		List<PhysicalLocation> expected = new ArrayList<>();
		expected.add(createStructure());
		
		when(locationRepository.findStructuresByParentAndServerVersion("3734", 15622112121L)).thenReturn(expected);
		
		List<PhysicalLocation> locations = locationService.findStructuresByParentAndServerVersion("3734", 15622112121L);
		verify(locationRepository).findStructuresByParentAndServerVersion("3734", 15622112121L);
		verifyNoMoreInteractions(locationRepository);
		
		assertEquals(1, locations.size());
		PhysicalLocation structure = locations.get(0);
		
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", structure.getProperties().getUid());
		assertEquals("21384443", structure.getProperties().getCode());
		assertEquals("Residential Structure", structure.getProperties().getType());
		
		when(locationRepository.findStructuresByParentAndServerVersion("3734,001", 15622112121L)).thenReturn(expected);
		locations = locationService.findStructuresByParentAndServerVersion("3734,001", 15622112121L);
		verify(locationRepository).findStructuresByParentAndServerVersion("3734,001", 15622112121L);
		verifyNoMoreInteractions(locationRepository);
		assertEquals(1, locations.size());
		structure = locations.get(0);
		assertEquals("41587456-b7c8-4c4e-b433-23a786f742fc", structure.getProperties().getUid());
		assertEquals("21384443", structure.getProperties().getCode());
		assertEquals("Residential Structure", structure.getProperties().getType());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindStructuresByParentAndServerVersionWithoutParentID() {
		
		locationService.findStructuresByParentAndServerVersion("", 15622112121L);
		
	}
	
	@Test
	public void testSaveLocations() {
		List<PhysicalLocation> expectedLocations = new ArrayList<>();
		PhysicalLocation physicalLocation = createStructure();
		physicalLocation.setId("12323");
		expectedLocations.add(physicalLocation);
		expectedLocations.add(createStructure());
		expectedLocations.add(createStructure());
		
		when(locationRepository.get("12323", true, false)).thenReturn(physicalLocation);
		locationService.saveLocations(expectedLocations, true);
		
		verify(locationRepository, times(2)).add(argumentCaptor.capture());
		verify(locationRepository, times(1)).update(argumentCaptor.capture());
		for (PhysicalLocation location : argumentCaptor.getAllValues()) {
			assertTrue(location.isJurisdiction());
		}
		
	}
	
	@Test
	public void testFindStructuresWithinRadius() {
		
		double latitude = -14.1619809;
		double longitude = 32.5978597;
		
		Collection<StructureDetails> expectedDetails = new ArrayList<>();
		
		StructureDetails structure = new StructureDetails(UUID.randomUUID().toString(), "3221", "Mosquito Point");
		expectedDetails.add(structure);
		
		when(locationRepository.findStructureAndFamilyDetails(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
		        .thenReturn(expectedDetails);
		Collection<StructureDetails> detailsFromService = locationService.findStructuresWithinRadius(latitude, longitude,
		    1000);
		
		assertEquals(expectedDetails, detailsFromService);
		verify(locationRepository).findStructureAndFamilyDetails(latitude, longitude, 1000);
		
	}
	
	@Test
	public void testFindLocationsByProperties() {
		List<PhysicalLocation> expectedLocations = Collections.singletonList(createLocation());
		
		String parentId = UUID.randomUUID().toString();
		Map<String, String> properties = new HashMap<>();
		when(locationRepository.findLocationsByProperties(true, parentId, properties)).thenReturn(expectedLocations);
		List<PhysicalLocation> locations = locationService.findLocationsByProperties(true, parentId, properties);
		verify(locationRepository).findLocationsByProperties(true, parentId, properties);
		assertEquals(1, locations.size());
		assertEquals(expectedLocations, locations);
		
	}
	
	@Test
	public void testFindStructuresByProperties() {
		List<PhysicalLocation> expectedLocations = Collections.singletonList(createStructure());
		String parentId = UUID.randomUUID().toString();
		Map<String, String> properties = new HashMap<>();
		when(locationRepository.findStructuresByProperties(false, parentId, properties)).thenReturn(expectedLocations);
		List<PhysicalLocation> locations = locationService.findStructuresByProperties(false, parentId, properties);
		verify(locationRepository).findStructuresByProperties(false, parentId, properties);
		assertEquals(1, locations.size());
		assertEquals(expectedLocations, locations);
	}
	
	@Test
	public void testFindLocationsById() {
		List<PhysicalLocation> expectedLocations = Collections.singletonList(createLocation());
		
		List<String> locationIds = new ArrayList<>();
		when(locationRepository.findLocationsByIds(true, locationIds,null)).thenReturn(expectedLocations);
		List<PhysicalLocation> locations = locationService.findLocationsByIds(true, locationIds);
		verify(locationRepository).findLocationsByIds(true, locationIds,null);
		assertEquals(1, locations.size());
		assertEquals(expectedLocations, locations);
		
	}
	@Test
	public void testFindLocationsByIdWithServerVersion() {
		List<PhysicalLocation> expectedLocations = Collections.singletonList(createLocation());
		
		List<String> locationIds = Arrays.asList("id1","Id2");
		long serverVersion=1234;
		when(locationRepository.findLocationsByIds(true, locationIds,serverVersion)).thenReturn(expectedLocations);
		List<PhysicalLocation> locations = locationService.findLocationsByIds(true, locationIds,serverVersion);
		verify(locationRepository).findLocationsByIds(true, locationIds,serverVersion);
		assertEquals(1, locations.size());
		assertEquals(expectedLocations, locations);
		
	}
	
	@Test
	public void testFindAllStructureIds() {
		List<String> expectedStructureIds = new ArrayList<>();
		expectedStructureIds.add("Structure-1");
		expectedStructureIds.add("Structure-2");
		Pair<List<String>, Long> idsModel = Pair.of(expectedStructureIds, 1234l);
		
		when(locationRepository.findAllStructureIds(anyLong(), anyInt())).thenReturn(idsModel);
		Pair<List<String>, Long> actualIdModels = locationService.findAllStructureIds(0l, 2);
		
		List<String> actualStructureIds = actualIdModels.getLeft();
		
		verify(locationRepository).findAllStructureIds(0l, 2);
		assertEquals(2, actualStructureIds.size());
		assertEquals(expectedStructureIds.get(0), actualStructureIds.get(0));
		assertEquals(expectedStructureIds.get(1), actualStructureIds.get(1));
		
	}
	
	@Test
	public void testFindLocationDetailsByPlanId() {
		Set<LocationDetail> expectedLocationDetails = new HashSet<>();
		LocationDetail locationDetail = LocationDetail.builder().identifier("identifier-1").name("location-one").build();
		expectedLocationDetails.add(locationDetail);
		
		when(locationRepository.findLocationDetailsByPlanId("identifier-1")).thenReturn(expectedLocationDetails);
		Set<LocationDetail> actualLocationDetails = locationService.findLocationDetailsByPlanId("identifier-1");
		
		verify(locationRepository).findLocationDetailsByPlanId(anyString());
		assertEquals(1, actualLocationDetails.size());
		LocationDetail actuallocationDetail = actualLocationDetails.iterator().next();
		assertEquals(actuallocationDetail.getIdentifier(), "identifier-1");
		assertEquals(actuallocationDetail.getName(), "location-one");
		
	}
	
	@Test
	public void testFindAllLocationIds() {
		List<String> expectedLocationIds = new ArrayList<>();
		expectedLocationIds.add("Location-1");
		expectedLocationIds.add("Location-2");
		Pair<List<String>, Long> idsModel = Pair.of(expectedLocationIds, 1234l);
		
		when(locationRepository.findAllLocationIds(anyLong(), anyInt())).thenReturn(idsModel);
		Pair<List<String>, Long> actualIdsModelList = locationService.findAllLocationIds(0l, 10);
		
		List<String> actualLocationIds = actualIdsModelList.getLeft();
		verify(locationRepository).findAllLocationIds(0l, 10);
		assertEquals(2, actualLocationIds.size());
		assertEquals(expectedLocationIds.get(0), actualLocationIds.get(0));
		assertEquals(expectedLocationIds.get(1), actualLocationIds.get(1));
		assertEquals(1234l, actualIdsModelList.getRight().longValue());
		
	}
	
	private PhysicalLocation createLocation() {
		PhysicalLocation parentLocation = gson.fromJson(parentJson, PhysicalLocation.class);
		parentLocation.setJurisdiction(true);
		return parentLocation;
	}
	
	private PhysicalLocation createStructure() {
		return gson.fromJson(structureJson, PhysicalLocation.class);
	}
	
	@Test
	public void testSearchlLocationsWithFilters() {
		LocationSearchBean locationSearchBean = new LocationSearchBean();
		locationSearchBean.setName("a");
		locationSearchBean.setLocationTagId(2l);
		locationSearchBean.setPageSize(20);
		List<PhysicalLocation> expectedLocations = new ArrayList<>();
		expectedLocations.add(createLocation());
		when(locationRepository.searchLocations(locationSearchBean)).thenReturn(expectedLocations);
		List<PhysicalLocation> actutalLocations = locationService.searchLocations(locationSearchBean);
		verify(locationRepository).searchLocations(locationSearchBean);
		assertEquals(1, actutalLocations.size());
		assertEquals("Feature", actutalLocations.get(0).getType());
	}
	
	@Test
	public void testShouldSearchCountLocation() {
		LocationSearchBean locationSearchBean = new LocationSearchBean();
		when(locationRepository.countSearchLocations(locationSearchBean)).thenReturn(1);
		int actutalLocations = locationService.countSearchLocations(locationSearchBean);
		verify(locationRepository).countSearchLocations(locationSearchBean);
		assertEquals(1, actutalLocations);
	}
	
	@Test
	public void testbuildLocationHierachy() {
		Set<String> identifiers = new HashSet<>(Arrays.asList("1234", "21"));
		Set<LocationDetail> locationDetails = new HashSet<>();
		LocationDetail kenya = LocationDetail.builder().name("Kenya").id(1l).identifier("254").tags("Country").build();
		LocationDetail nairobi = LocationDetail.builder().name("Nairobi").id(2l).identifier("252-020").parentId("254")
		        .tags("City,Zone").build();
		locationDetails.add(kenya);
		locationDetails.add(nairobi);
		when(locationRepository.findParentLocationsInclusive(identifiers, false)).thenReturn(locationDetails);
		LocationTree tree = locationService.buildLocationHierachy(identifiers, false, false);
		verify(locationRepository).findParentLocationsInclusive(identifiers, false);
		assertNotNull(tree);
		assertEquals(1, tree.getLocationsHierarchy().size());
		TreeNode<String, Location> countryNode = tree.getLocationsHierarchy().get(kenya.getIdentifier());
		assertNotNull(countryNode);
		assertEquals(kenya.getIdentifier(), countryNode.getId());
		assertEquals(kenya.getName(), countryNode.getLabel());
		assertNull(countryNode.getParent());
		assertEquals(kenya.getTags(), countryNode.getNode().getTags().iterator().next());
		assertEquals(1, countryNode.getChildren().size());
		
		TreeNode<String, Location> cityNode = countryNode.getChildren().get(nairobi.getIdentifier());
		assertNotNull(cityNode);
		assertEquals(nairobi.getIdentifier(), cityNode.getId());
		assertEquals(nairobi.getName(), cityNode.getLabel());
		assertNull(cityNode.getChildren());
		assertEquals(kenya.getIdentifier(), cityNode.getParent());
		
		Set<String> tags = cityNode.getNode().getTags();
		assertEquals(2, tags.size());
		assertTrue(tags.contains("City"));
		assertTrue(tags.contains("Zone"));
		
	}
	
	@Test
	public void testCountStructuresByParentAndServerVersion() {
		
		when(locationRepository.countStructuresByParentAndServerVersion("3734", 15622112121L)).thenReturn(12l);
		
		Long locations = locationService.countStructuresByParentAndServerVersion("3734", 15622112121L);
		verify(locationRepository).countStructuresByParentAndServerVersion("3734", 15622112121L);
		verifyNoMoreInteractions(locationRepository);
		
		assertEquals(12, locations.longValue());
		
		when(locationRepository.countStructuresByParentAndServerVersion("3734,001", 15622112121L)).thenReturn(15l);
		locations = locationService.countStructuresByParentAndServerVersion("3734,001", 15622112121L);
		verify(locationRepository).countStructuresByParentAndServerVersion("3734,001", 15622112121L);
		verifyNoMoreInteractions(locationRepository);
		assertEquals(15, locations.longValue());
	}
	
	@Test
	public void testCountFindLocationsByServerVersion() {
		when(locationRepository.countLocationsByServerVersion(123l)).thenReturn(4l);
		
		Long locations = locationService.countLocationsByServerVersion(123l);
		verify(locationRepository).countLocationsByServerVersion(123l);
		verifyNoMoreInteractions(locationRepository);
		
		assertEquals(4, locations.longValue());
		
	}
	
	@Test
	public void testCountLocationsByNames() {
		String locationNames = "01_5";
		Long locations = locationService.countLocationsByNames(locationNames, 0l);
		assertEquals(0, locations.longValue());
		
		when(locationService.countLocationsByNames(locationNames, 0l)).thenReturn(3l);
		locations = locationService.countLocationsByNames(locationNames, 0l);
		assertEquals(3, locations.longValue());
		
		//		search with more than one name
		locationNames = "01_5,other_location_name";
		when(locationService.countLocationsByNames(locationNames, 0l)).thenReturn(2l);
		locations = locationService.countLocationsByNames(locationNames, 0l);
		assertEquals(2, locations.longValue());
		
	}
	
	@Test
	public void testBuildLocationHierachyFromLocation() {
		String locationId = "1";
		
		Set<LocationDetail> locationDetails = new HashSet<>();
		
		LocationDetail country = LocationDetail.builder().name("Country 1").id(2l).identifier("1").tags("Country").build();
		LocationDetail province1 = LocationDetail.builder().name("Province 1").id(3l).identifier("11").parentId("1")
		        .tags("Province").build();
		LocationDetail province2 = LocationDetail.builder().name("Province 2").id(4l).identifier("12").parentId("1")
		        .tags("Province").build();
		LocationDetail district1 = LocationDetail.builder().name("District 1").id(5l).identifier("111").parentId("11")
		        .tags("District").build();
		LocationDetail district2 = LocationDetail.builder().name("District 2").id(6l).identifier("121").parentId("12")
		        .tags("District").build();
		LocationDetail district3 = LocationDetail.builder().name("District 3").id(7l).identifier("122").parentId("12")
		        .tags("District").build();
		
		locationDetails.add(country);
		locationDetails.add(province1);
		locationDetails.add(province2);
		locationDetails.add(district1);
		locationDetails.add(district2);
		locationDetails.add(district3);
		
		Boolean returnTags = false;
		
		when(locationRepository.findLocationWithDescendants(locationId, returnTags)).thenReturn(locationDetails);
		
		LocationTree tree = locationService.buildLocationHierachyFromLocation(locationId, false);
		
		verify(locationRepository).findLocationWithDescendants(locationId, returnTags);
		assertNotNull(tree);
		assertEquals(1, tree.getLocationsHierarchy().size());
		
		TreeNode<String, Location> level0Node = tree.getLocationsHierarchy().get(country.getIdentifier());
		assertNotNull(level0Node);
		assertEquals(country.getIdentifier(), level0Node.getId());
		assertEquals(country.getName(), level0Node.getLabel());
		assertNull(level0Node.getParent());
		assertEquals(country.getTags(), level0Node.getNode().getTags().iterator().next());
		assertEquals(2, level0Node.getChildren().size());
		
		TreeNode<String, Location> level1Node = level0Node.getChildren().get(province2.getIdentifier());
		assertNotNull(level1Node);
		assertEquals(province2.getIdentifier(), level1Node.getId());
		assertEquals(province2.getName(), level1Node.getLabel());
		assertEquals(2, level1Node.getChildren().size());
		assertEquals(country.getIdentifier(), level1Node.getParent());
		
		TreeNode<String, Location> level2Node = level1Node.getChildren().get(district2.getIdentifier());
		assertNotNull(level2Node);
		assertEquals(district2.getIdentifier(), level2Node.getId());
		assertEquals(district2.getName(), level2Node.getLabel());
		assertNull(level2Node.getChildren());
		assertEquals(province2.getIdentifier(), level2Node.getParent());
		
		Set<String> tags = level2Node.getNode().getTags();
		assertEquals(1, tags.size());
		assertTrue(tags.contains("District"));
	}
	
	@Test
	public void testBuildLocationHierachyFromLocationWithStructureCounts() {
		String locationId = "1";
		Set<LocationDetail> locationDetails = new LinkedHashSet<>();
		
		LocationDetail country = LocationDetail.builder().name("Country 1").id(2l).identifier("1").tags("Country")
		        .geographicLevel(0).build();
		LocationDetail province1 = LocationDetail.builder().name("Province 1").id(3l).identifier("11").parentId("1")
		        .tags("Province").geographicLevel(1).build();
		LocationDetail province2 = LocationDetail.builder().name("Province 2").id(4l).identifier("12").parentId("1")
		        .tags("Province").geographicLevel(1).build();
		LocationDetail district1 = LocationDetail.builder().name("District 1").id(5l).identifier("111").parentId("11")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district2 = LocationDetail.builder().name("District 2").id(6l).identifier("121").parentId("12")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district3 = LocationDetail.builder().name("District 3").id(7l).identifier("122").parentId("12")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district4 = LocationDetail.builder().name("District 4").id(8l).identifier("123").parentId("12")
		        .tags("District").geographicLevel(2).build(); // Test what happens when child location has no structures
		
		// records are ordered by level in the db query
		locationDetails.add(district4);
		locationDetails.add(district3);
		locationDetails.add(district2);
		locationDetails.add(district1);
		locationDetails.add(province2);
		locationDetails.add(province1);
		locationDetails.add(country);
		
		Set<String> locationIdentifiers = new HashSet<>();
		locationIdentifiers.add(country.getIdentifier());
		locationIdentifiers.add(province1.getIdentifier());
		locationIdentifiers.add(province2.getIdentifier());
		locationIdentifiers.add(district1.getIdentifier());
		locationIdentifiers.add(district2.getIdentifier());
		locationIdentifiers.add(district3.getIdentifier());
		locationIdentifiers.add(district4.getIdentifier());
		
		when(locationRepository.findLocationWithDescendants(locationId, false)).thenReturn(locationDetails);
		
		List<StructureCount> structureCounts = new ArrayList<>();
		StructureCount structureCountD1 = StructureCount.builder().parentId(district1.getIdentifier()).count(10).build();
		StructureCount structureCountD2 = StructureCount.builder().parentId(district2.getIdentifier()).count(5).build();
		StructureCount structureCountD3 = StructureCount.builder().parentId(district3.getIdentifier()).count(8).build();
		structureCounts.add(structureCountD1);
		structureCounts.add(structureCountD2);
		structureCounts.add(structureCountD3);
		
		when(locationRepository.findStructureCountsForLocation(locationIdentifiers)).thenReturn(structureCounts);
		
		LocationTree tree = locationService.buildLocationHierachyFromLocation(locationId, true);
		
		TreeNode<String, Location> countryNode = tree.getLocationsHierarchy().get(country.getIdentifier());
		assertNotNull(countryNode);
		assertEquals(country.getIdentifier(), countryNode.getId());
		assertEquals(country.getName(), countryNode.getLabel());
		assertNull(countryNode.getParent());
		assertEquals(country.getTags(), countryNode.getNode().getTags().iterator().next());
		assertEquals(2, countryNode.getChildren().size());
		assertEquals(0, countryNode.getNode().getAttribute("geographicLevel"));
		assertEquals(23, countryNode.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> province1Node = countryNode.getChildren().get(province1.getIdentifier());
		assertNotNull(province1Node);
		verifyLocationData(province1Node, province1, country, 1, true);
		assertEquals(10, province1Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> province2Node = countryNode.getChildren().get(province2.getIdentifier());
		verifyLocationData(province2Node, province2, country, 1, true);
		assertEquals(13, province2Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district1Node = province1Node.getChildren().get(district1.getIdentifier());
		verifyLocationData(district1Node, district1, province1, 2, false);
		assertEquals(10, district1Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district2Node = province2Node.getChildren().get(district2.getIdentifier());
		verifyLocationData(district2Node, district2, province2, 2, false);
		assertEquals(5, district2Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district3Node = province2Node.getChildren().get(district3.getIdentifier());
		verifyLocationData(district3Node, district3, province2, 2, false);
		assertEquals(8, district3Node.getNode().getAttribute(STRUCTURE_COUNT));
	}
	
	@Test
	public void testBuildLocationHierarchy() {
		Set<LocationDetail> locationDetails = new HashSet<>();
		
		LocationDetail country = LocationDetail.builder().name("Country 1").id(2l).identifier("1").tags("Country")
		        .geographicLevel(0).build();
		LocationDetail province1 = LocationDetail.builder().name("Province 1").id(3l).identifier("11").parentId("1")
		        .tags("Province").geographicLevel(1).build();
		LocationDetail province2 = LocationDetail.builder().name("Province 2").id(4l).identifier("12").parentId("1")
		        .tags("Province").geographicLevel(1).build();
		LocationDetail district1 = LocationDetail.builder().name("District 1").id(5l).identifier("111").parentId("11")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district2 = LocationDetail.builder().name("District 2").id(6l).identifier("121").parentId("12")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district3 = LocationDetail.builder().name("District 3").id(7l).identifier("122").parentId("12")
		        .tags("District").geographicLevel(2).build();
		
		locationDetails.add(country);
		locationDetails.add(province1);
		locationDetails.add(province2);
		locationDetails.add(district1);
		locationDetails.add(district2);
		locationDetails.add(district3);
		
		Set<String> locationIdentifiers = new HashSet<>();
		locationIdentifiers.add(district1.getIdentifier());
		locationIdentifiers.add(district2.getIdentifier());
		locationIdentifiers.add(district2.getIdentifier());
		
		when(locationRepository.findParentLocationsInclusive(locationIdentifiers, true)).thenReturn(locationDetails);
		
		LocationTree tree = locationService.buildLocationHierachy(locationIdentifiers, false, true);
		
		TreeNode<String, Location> countryNode = tree.getLocationsHierarchy().get(country.getIdentifier());
		assertNotNull(countryNode);
		assertEquals(country.getIdentifier(), countryNode.getId());
		assertEquals(country.getName(), countryNode.getLabel());
		assertNull(countryNode.getParent());
		assertEquals(country.getTags(), countryNode.getNode().getTags().iterator().next());
		assertEquals(2, countryNode.getChildren().size());
		assertNull(countryNode.getNode().getAttribute(STRUCTURE_COUNT));
		assertEquals(0, countryNode.getNode().getAttribute("geographicLevel"));
		
		TreeNode<String, Location> province1Node = countryNode.getChildren().get(province1.getIdentifier());
		assertNotNull(province1Node);
		verifyLocationData(province1Node, province1, country, 1, true);
		assertNull(province1Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> province2Node = countryNode.getChildren().get(province2.getIdentifier());
		verifyLocationData(province2Node, province2, country, 1, true);
		assertNull(province2Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district1Node = province1Node.getChildren().get(district1.getIdentifier());
		verifyLocationData(district1Node, district1, province1, 2, false);
		assertNull(district1Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district2Node = province2Node.getChildren().get(district2.getIdentifier());
		verifyLocationData(district2Node, district2, province2, 2, false);
		assertNull(district2Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district3Node = province2Node.getChildren().get(district3.getIdentifier());
		verifyLocationData(district3Node, district3, province2, 2, false);
		assertNull(district3Node.getNode().getAttribute(STRUCTURE_COUNT));
	}
	
	@Test
	public void testBuildLocationHierarchyWithStructureCounts() {
		Set<LocationDetail> locationDetails = new LinkedHashSet<>();
		
		LocationDetail country = LocationDetail.builder().name("Country 1").id(2l).identifier("1").tags("Country")
		        .geographicLevel(0).build();
		LocationDetail province1 = LocationDetail.builder().name("Province 1").id(3l).identifier("11").parentId("1")
		        .tags("Province").geographicLevel(1).build();
		LocationDetail province2 = LocationDetail.builder().name("Province 2").id(4l).identifier("12").parentId("1")
		        .tags("Province").geographicLevel(1).build();
		LocationDetail district1 = LocationDetail.builder().name("District 1").id(5l).identifier("111").parentId("11")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district2 = LocationDetail.builder().name("District 2").id(6l).identifier("121").parentId("12")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district3 = LocationDetail.builder().name("District 3").id(7l).identifier("122").parentId("12")
		        .tags("District").geographicLevel(2).build();
		LocationDetail district4 = LocationDetail.builder().name("District 4").id(8l).identifier("123").parentId("12")
		        .tags("District").geographicLevel(2).build(); // Test what happens when child not has no structures
		
		// records are ordered by level in the db query
		locationDetails.add(district4);
		locationDetails.add(district3);
		locationDetails.add(district2);
		locationDetails.add(district1);
		locationDetails.add(province2);
		locationDetails.add(province1);
		locationDetails.add(country);
		
		Set<String> locationIdentifiers = new HashSet<>();
		locationIdentifiers.add(country.getIdentifier());
		locationIdentifiers.add(province1.getIdentifier());
		locationIdentifiers.add(province2.getIdentifier());
		locationIdentifiers.add(district1.getIdentifier());
		locationIdentifiers.add(district2.getIdentifier());
		locationIdentifiers.add(district3.getIdentifier());
		locationIdentifiers.add(district4.getIdentifier());
		
		when(locationRepository.findParentLocationsInclusive(locationIdentifiers, false)).thenReturn(locationDetails);
		
		List<StructureCount> structureCounts = new ArrayList<>();
		StructureCount structureCountD1 = StructureCount.builder().parentId(district1.getIdentifier()).count(10).build();
		StructureCount structureCountD2 = StructureCount.builder().parentId(district2.getIdentifier()).count(5).build();
		StructureCount structureCountD3 = StructureCount.builder().parentId(district3.getIdentifier()).count(8).build();
		structureCounts.add(structureCountD1);
		structureCounts.add(structureCountD2);
		structureCounts.add(structureCountD3);
		
		when(locationRepository.findStructureCountsForLocation(locationIdentifiers)).thenReturn(structureCounts);
		
		LocationTree tree = locationService.buildLocationHierachy(locationIdentifiers, true, false);
		
		TreeNode<String, Location> countryNode = tree.getLocationsHierarchy().get(country.getIdentifier());
		assertNotNull(countryNode);
		assertEquals(country.getIdentifier(), countryNode.getId());
		assertEquals(country.getName(), countryNode.getLabel());
		assertNull(countryNode.getParent());
		assertEquals(country.getTags(), countryNode.getNode().getTags().iterator().next());
		assertEquals(2, countryNode.getChildren().size());
		assertEquals(0, countryNode.getNode().getAttribute("geographicLevel"));
		assertEquals(23, countryNode.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> province1Node = countryNode.getChildren().get(province1.getIdentifier());
		assertNotNull(province1Node);
		verifyLocationData(province1Node, province1, country, 1, true);
		assertEquals(10, province1Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> province2Node = countryNode.getChildren().get(province2.getIdentifier());
		verifyLocationData(province2Node, province2, country, 1, true);
		assertEquals(13, province2Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district1Node = province1Node.getChildren().get(district1.getIdentifier());
		verifyLocationData(district1Node, district1, province1, 2, false);
		assertEquals(10, district1Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district2Node = province2Node.getChildren().get(district2.getIdentifier());
		verifyLocationData(district2Node, district2, province2, 2, false);
		assertEquals(5, district2Node.getNode().getAttribute(STRUCTURE_COUNT));
		
		TreeNode<String, Location> district3Node = province2Node.getChildren().get(district3.getIdentifier());
		verifyLocationData(district3Node, district3, province2, 2, false);
		assertEquals(8, district3Node.getNode().getAttribute(STRUCTURE_COUNT));
	}
	
	private void verifyLocationData(TreeNode<String, Location> node, LocationDetail locationDetail, LocationDetail parentLD,
	        int geographicLevel, boolean hasChildren) {
		assertNotNull(node);
		assertEquals(locationDetail.getIdentifier(), node.getId());
		assertEquals(locationDetail.getName(), node.getLabel());
		if (!hasChildren) {
			assertNull(node.getChildren());
		}
		assertEquals(parentLD.getIdentifier(), node.getParent());
		assertEquals(geographicLevel, node.getNode().getAttribute("geographicLevel"));
	}
	
	@Test
	public void testfindLocationByIdWithChildren() {
		List<PhysicalLocation> expected = new ArrayList<>();
		expected.add(createLocation());
		when(locationRepository.findLocationByIdWithChildren(false, "1332232", 1000)).thenReturn(expected);
		assertEquals(expected, locationService.findLocationByIdWithChildren(false, "1332232", 1000));
		verify(locationRepository).findLocationByIdWithChildren(false, "1332232", 1000);
	}
	
	@Test
	public void testfindLocationByIdsWithChildren() {
		List<PhysicalLocation> expected = new ArrayList<>();
		expected.add(createLocation());
		Set<String> ids = new HashSet<>(Arrays.asList("123","23435463","9888787"));
		when(locationRepository.findLocationByIdsWithChildren(false, ids, 1000)).thenReturn(expected);
		assertEquals(expected, locationService.findLocationByIdsWithChildren(false, ids, 1000));
		verify(locationRepository).findLocationByIdsWithChildren(false, ids, 1000);
	}

	@Test
	public void testBuildLocationHeirarchyWithAncestors() {
		Set<String> locationIds = new HashSet<>();
		String locationId = "1";
		locationIds.add(locationId);

		Set<LocationDetail> locationDetails = new HashSet<>();

		LocationDetail country = LocationDetail.builder().name("Country 1").id(2l).identifier("1").tags("Country").build();
		LocationDetail province1 = LocationDetail.builder().name("Province 1").id(3l).identifier("11").parentId("1")
				.tags("Province").build();
		LocationDetail province2 = LocationDetail.builder().name("Province 2").id(4l).identifier("12").parentId("1")
				.tags("Province").build();
		LocationDetail district1 = LocationDetail.builder().name("District 1").id(5l).identifier("111").parentId("11")
				.tags("District").build();
		LocationDetail district2 = LocationDetail.builder().name("District 2").id(6l).identifier("121").parentId("12")
				.tags("District").build();
		LocationDetail district3 = LocationDetail.builder().name("District 3").id(7l).identifier("122").parentId("12")
				.tags("District").build();

		locationDetails.add(country);
		locationDetails.add(province1);
		locationDetails.add(province2);
		locationDetails.add(district1);
		locationDetails.add(district2);
		locationDetails.add(district3);

		when(locationRepository.findParentLocationsInclusive(locationIds)).thenReturn(locationDetails);

		Set<LocationDetail> locationDetailsSet = locationService.buildLocationHeirarchyWithAncestors(locationId);

		verify(locationRepository).findParentLocationsInclusive(locationIds);
		assertNotNull(locationDetailsSet);
		assertEquals(6, locationDetailsSet.size());
		LocationDetail actuallocationDetail = locationDetailsSet.iterator().next();
		assertEquals("Province 1", actuallocationDetail.getName());
		assertEquals("Province", actuallocationDetail.getTags());
	}


	@Test
	public void testBuildLocationTreeHierachyWithAncestors() {
		Set<String> locationIds = new HashSet<>();
		String locationId = "1";
		locationIds.add(locationId);

		Set<LocationDetail> locationDetails = new HashSet<>();

		LocationDetail country = LocationDetail.builder().name("Country 1").id(2l).identifier("1").tags("Country").build();
		LocationDetail province1 = LocationDetail.builder().name("Province 1").id(3l).identifier("11").parentId("1")
				.tags("Province").build();
		LocationDetail province2 = LocationDetail.builder().name("Province 2").id(4l).identifier("12").parentId("1")
				.tags("Province").build();
		LocationDetail district1 = LocationDetail.builder().name("District 1").id(5l).identifier("111").parentId("11")
				.tags("District").build();
		LocationDetail district2 = LocationDetail.builder().name("District 2").id(6l).identifier("121").parentId("12")
				.tags("District").build();
		LocationDetail district3 = LocationDetail.builder().name("District 3").id(7l).identifier("122").parentId("12")
				.tags("District").build();

		locationDetails.add(country);
		locationDetails.add(province1);
		locationDetails.add(province2);
		locationDetails.add(district1);
		locationDetails.add(district2);
		locationDetails.add(district3);

		when(locationRepository.findParentLocationsInclusive(locationIds)).thenReturn(locationDetails);

		LocationTree locationTree = locationService.buildLocationTreeHierachyWithAncestors(locationId, false);

		verify(locationRepository).findParentLocationsInclusive(locationIds);
		assertNotNull(locationTree);
		assertEquals(1, locationTree.getLocationsHierarchy().size());

		TreeNode<String, Location> level0Node = locationTree.getLocationsHierarchy().get(country.getIdentifier());
		assertNotNull(level0Node);
		assertEquals(country.getIdentifier(), level0Node.getId());
		assertEquals(country.getName(), level0Node.getLabel());
		assertNull(level0Node.getParent());
		assertEquals(country.getTags(), level0Node.getNode().getTags().iterator().next());
		assertEquals(2, level0Node.getChildren().size());

		TreeNode<String, Location> level1Node = level0Node.getChildren().get(province2.getIdentifier());
		assertNotNull(level1Node);
		assertEquals(province2.getIdentifier(), level1Node.getId());
		assertEquals(province2.getName(), level1Node.getLabel());
		assertEquals(2, level1Node.getChildren().size());
		assertEquals(country.getIdentifier(), level1Node.getParent());

		TreeNode<String, Location> level2Node = level1Node.getChildren().get(district2.getIdentifier());
		assertNotNull(level2Node);
		assertEquals(district2.getIdentifier(), level2Node.getId());
		assertEquals(district2.getName(), level2Node.getLabel());
		assertNull(level2Node.getChildren());
		assertEquals(province2.getIdentifier(), level2Node.getParent());

		Set<String> tags = level2Node.getNode().getTags();
		assertEquals(1, tags.size());
		assertTrue(tags.contains("District"));
	}

}
