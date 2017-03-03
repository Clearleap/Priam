package com.netflix.priam.backup.identity;

import com.netflix.priam.FakeConfiguration;
import com.netflix.priam.FakeMembership;
import com.netflix.priam.FakePriamInstanceFactory;
import com.netflix.priam.identity.IMembership;
import com.netflix.priam.identity.IPriamInstanceFactory;
import com.netflix.priam.identity.InstanceIdentity;
import com.netflix.priam.identity.token.DeadTokenRetriever;
import com.netflix.priam.identity.token.NewTokenRetriever;
import com.netflix.priam.identity.token.PreGeneratedTokenRetriever;
import com.netflix.priam.utils.FakeSleeper;
import com.netflix.priam.utils.ITokenManager;
import com.netflix.priam.utils.Sleeper;
import com.netflix.priam.utils.TokenManager;

import org.junit.Before;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;

@Ignore
public abstract class InstanceTestUtils
{

    List<String> instances = new ArrayList<String>();
    IMembership membership;
    FakeConfiguration config;
    IPriamInstanceFactory factory;
    InstanceIdentity identity;
    Sleeper sleeper;
    DeadTokenRetriever deadTokenRetriever;
    PreGeneratedTokenRetriever preGeneratedTokenRetriever;
	NewTokenRetriever newTokenRetriever;
	private static final ITokenManager tokenManager = new TokenManager();

    @Before
    public void setup()
    {
        instances.add("fakeinstance1");
        instances.add("fakeinstance2");
        instances.add("fakeinstance3");
        instances.add("fakeinstance4");
        instances.add("fakeinstance5");
        instances.add("fakeinstance6");
        instances.add("fakeinstance7");
        instances.add("fakeinstance8");
        instances.add("fakeinstance9");
        instances.add("fakeinstance10");

        config = new FakeConfiguration("fake", "fake-app", "az1", "fakeinstance1");
        membership = new FakeMembership(instances,config);
        factory = new FakePriamInstanceFactory(config);
        sleeper = new FakeSleeper();
        this.deadTokenRetriever = new DeadTokenRetriever(factory, membership, config, sleeper);
        this.preGeneratedTokenRetriever = new PreGeneratedTokenRetriever(factory, membership, config, sleeper);
        this.newTokenRetriever = new NewTokenRetriever(factory, membership, config, sleeper, tokenManager);
    }

	// won't create "loop-sided" clusters, will create an instance in each az until there aren't enough
	// left in map to put one in all az's.. adjust the instance map above and azs in FakeConf to test your deployment scenario.
    public void createInstances() throws Exception
    {
		int pos = 0;
		for (int x=0;x < membership.getRacMembershipSize()/membership.getRacCount(); x++) {
			for (String rac : config.getRacs()) {
				createInstanceIdentity(rac, instances.get(pos));
				pos++;
			}
		}
    }
    
    protected InstanceIdentity createInstanceIdentity(String zone, String instanceId) throws Exception
    {
        config.zone = zone;
        config.instance_id = instanceId;
        return new InstanceIdentity(factory, membership, config, sleeper, new TokenManager()
        , this.deadTokenRetriever
        , this.preGeneratedTokenRetriever
        , this.newTokenRetriever
        );
    }
}
