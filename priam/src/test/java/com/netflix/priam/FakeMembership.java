package com.netflix.priam;

import java.util.Collection;
import java.util.List;

import com.netflix.priam.identity.IMembership;

public class FakeMembership implements IMembership
{

    private List<String> instances;
	private IConfiguration config;

    public FakeMembership(List<String> priamInstances, IConfiguration config)
    {
        this.instances = priamInstances;
		this.config = config;
    }
    
    public void setInstances( List<String> priamInstances)
    {
        this.instances = priamInstances;
    }

    @Override
    public List<String> getRacMembership()
    {
        return instances;
    }

    @Override
    public int getRacMembershipSize()
    {
        return (instances != null?instances.size():0);
    }

    @Override
    public int getRacCount()
    {
		return config.getRacs().size();
    }

    @Override
    public void addACL(Collection<String> listIPs, int from, int to)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeACL(Collection<String> listIPs, int from, int to)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<String> listACL(int from, int to)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void expandRacMembership(int count)
    {
        // TODO Auto-generated method stub
        
    }
}
