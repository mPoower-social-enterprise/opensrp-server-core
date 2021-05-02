package org.opensrp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.HouseholdId;
import org.opensrp.repository.HouseholdIdRepository;
import org.opensrp.repository.postgres.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

public class HouseholdIdServiceTest extends BaseRepositoryTest {
	
	private HouseholdIdService householdIdService;
	
	private Integer HOUSEHOLD_ID_LIMIT = 200;
	
	String householdIds = "[{\"village_id\":22,\"generated_code\":[\"0001\",\"0002\",\"0003\",\"0004\",\"0005\",\"0006\",\"0007\",\"0008\",\"0009\",\"0010\",\"0011\",\"0012\",\"0013\",\"0014\",\"0015\",\"0016\",\"0017\",\"0018\",\"0019\",\"0020\",\"0021\",\"0022\",\"0023\",\"0024\",\"0025\",\"0026\",\"0027\",\"0028\",\"0029\",\"0030\",\"0031\",\"0032\",\"0033\",\"0034\",\"0035\",\"0036\",\"0037\",\"0038\",\"0039\",\"0040\",\"0041\",\"0042\",\"0043\",\"0044\",\"0045\",\"0046\",\"0047\",\"0048\",\"0049\",\"0050\",\"0051\",\"0052\",\"0053\",\"0054\",\"0055\",\"0056\",\"0057\",\"0058\",\"0059\",\"0060\",\"0061\",\"0062\",\"0063\",\"0064\",\"0065\",\"0066\",\"0067\",\"0068\",\"0069\",\"0070\",\"0071\",\"0072\",\"0073\",\"0074\",\"0075\",\"0076\",\"0077\",\"0078\",\"0079\",\"0080\",\"0081\",\"0082\",\"0083\",\"0084\",\"0085\",\"0086\",\"0087\",\"0088\",\"0089\",\"0090\",\"0091\",\"0092\",\"0093\",\"0094\",\"0095\",\"0096\",\"0097\",\"0098\",\"0099\",\"0100\",\"0101\",\"0102\",\"0103\",\"0104\",\"0105\",\"0106\",\"0107\",\"0108\",\"0109\",\"0110\",\"0111\",\"0112\",\"0113\",\"0114\",\"0115\",\"0116\",\"0117\",\"0118\",\"0119\",\"0120\",\"0121\",\"0122\",\"0123\",\"0124\",\"0125\",\"0126\",\"0127\",\"0128\",\"0129\",\"0130\",\"0131\",\"0132\",\"0133\",\"0134\",\"0135\",\"0136\",\"0137\",\"0138\",\"0139\",\"0140\",\"0141\",\"0142\",\"0143\",\"0144\",\"0145\",\"0146\",\"0147\",\"0148\",\"0149\",\"0150\",\"0151\",\"0152\",\"0153\",\"0154\",\"0155\",\"0156\",\"0157\",\"0158\",\"0159\",\"0160\",\"0161\",\"0162\",\"0163\",\"0164\",\"0165\",\"0166\",\"0167\",\"0168\",\"0169\",\"0170\",\"0171\",\"0172\",\"0173\",\"0174\",\"0175\",\"0176\",\"0177\",\"0178\",\"0179\",\"0180\",\"0181\",\"0182\",\"0183\",\"0184\",\"0185\",\"0186\",\"0187\",\"0188\",\"0189\",\"0190\",\"0191\",\"0192\",\"0193\",\"0194\",\"0195\",\"0196\",\"0197\",\"0198\",\"0199\",\"0200\",\"0201\"]}]";
	
	String guetshouseholdIds = "[{\"village_id\":22,\"generated_code\":[\"00001\",\"00002\",\"00003\",\"00004\",\"00005\",\"00006\",\"00007\",\"00008\",\"00009\",\"00010\",\"00011\",\"00012\",\"00013\",\"00014\",\"00015\",\"00016\",\"00017\",\"00018\",\"00019\",\"00020\",\"00021\",\"00022\",\"00023\",\"00024\",\"00025\",\"00026\",\"00027\",\"00028\",\"00029\",\"00030\",\"00031\",\"00032\",\"00033\",\"00034\",\"00035\",\"00036\",\"00037\",\"00038\",\"00039\",\"00040\",\"00041\",\"00042\",\"00043\",\"00044\",\"00045\",\"00046\",\"00047\",\"00048\",\"00049\",\"00050\",\"00051\",\"00052\",\"00053\",\"00054\",\"00055\",\"00056\",\"00057\",\"00058\",\"00059\",\"00060\",\"00061\",\"00062\",\"00063\",\"00064\",\"00065\",\"00066\",\"00067\",\"00068\",\"00069\",\"00070\",\"00071\",\"00072\",\"00073\",\"00074\",\"00075\",\"00076\",\"00077\",\"00078\",\"00079\",\"00080\",\"00081\",\"00082\",\"00083\",\"00084\",\"00085\",\"00086\",\"00087\",\"00088\",\"00089\",\"00090\",\"00091\",\"00092\",\"00093\",\"00094\",\"00095\",\"00096\",\"00097\",\"00098\",\"00099\",\"00100\",\"00101\",\"00102\",\"00103\",\"00104\",\"00105\",\"00106\",\"00107\",\"00108\",\"00109\",\"00110\",\"00111\",\"00112\",\"00113\",\"00114\",\"00115\",\"00116\",\"00117\",\"00118\",\"00119\",\"00120\",\"00121\",\"00122\",\"00123\",\"00124\",\"00125\",\"00126\",\"00127\",\"00128\",\"00129\",\"00130\",\"00131\",\"00132\",\"00133\",\"00134\",\"00135\",\"00136\",\"00137\",\"00138\",\"00139\",\"00140\",\"00141\",\"00142\",\"00143\",\"00144\",\"00145\",\"00146\",\"00147\",\"00148\",\"00149\",\"00150\",\"00151\",\"00152\",\"00153\",\"00154\",\"00155\",\"00156\",\"00157\",\"00158\",\"00159\",\"00160\",\"00161\",\"00162\",\"00163\",\"00164\",\"00165\",\"00166\",\"00167\",\"00168\",\"00169\",\"00170\",\"00171\",\"00172\",\"00173\",\"00174\",\"00175\",\"00176\",\"00177\",\"00178\",\"00179\",\"00180\",\"00181\",\"00182\",\"00183\",\"00184\",\"00185\",\"00186\",\"00187\",\"00188\",\"00189\",\"00190\",\"00191\",\"00192\",\"00193\",\"00194\",\"00195\",\"00196\",\"00197\",\"00198\",\"00199\",\"00200\",\"00201\"]}]";
	
	@Autowired
	private HouseholdIdRepository householdIdRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.household_id", "core.household_id_guest");
	}
	
	@Before
	public void setUpPostgresRepository() {
		householdIdService = new HouseholdIdService(householdIdRepository);
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void testInsertHouseholdId() {
		int insert = householdIdService.insertHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
	}
	
	@Test
	public void testGetMaxHouseholdIdByLocation() {
		int insert = householdIdService.insertHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
		Integer maxhealthId = householdIdService.getMaxHouseholdIdByLocation(22);
		assertEquals(201, maxhealthId.intValue());
		
	}
	
	@Test
	public void testGetSeriesOfHouseholdId() {
		
		List<String> series = householdIdService.getSeriesOfHouseholdId(1);
		assertEquals(201, series.size());
		assertNotNull(series);
		
	}
	
	@Test
	public void testInsertGuestHouseholdId() {
		int insert = householdIdService.insertGuestHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
	}
	
	@Test
	public void testGetMaxGuestHouseholdIdByLocation() {
		int insert = householdIdService.insertGuestHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
		Integer maxhealthId = householdIdService.getMaxGuestHouseholdIdByLocation(22);
		assertEquals(201, maxhealthId.intValue());
		
	}
	
	@Test
	public void testGetSeriesOfGuestHouseholdId() {
		List<String> series = householdIdService.getSeriesOfGuestHouseholdId(1);
		assertEquals(201, series.size());
		assertNotNull(series);
		
	}
	
	@Test
	public void testGenerateHouseholdId() throws Exception {
		List<Integer> villageIds = new ArrayList<>();
		villageIds.add(22);
		JSONArray findHouseholdIds = householdIdService.generateHouseholdId(villageIds);
		assertEquals(householdIds, findHouseholdIds.toString());
		
	}
	
	@Test
	public void testGenerateGuestHouseholdId() throws Exception {
		List<Integer> villageIds = new ArrayList<>();
		villageIds.add(22);
		JSONArray findHouseholdIds = householdIdService.generateGuestHouseholdId(villageIds);
		
		assertEquals(guetshouseholdIds, findHouseholdIds.toString());
		
	}
	
	private HouseholdId generateHouseholdId() {
		HouseholdId healthId = new HouseholdId();
		healthId.setCreated(new Date());
		healthId.sethId(String.valueOf(1 + HOUSEHOLD_ID_LIMIT));
		healthId.setLocationId(22);
		healthId.setStatus(true);
		return healthId;
	}
}