# DBSCANImpOnMapApp

This app includes dbscan implementation.

First page takes inputs for Eps and MinPts to calculate clusters.

Algorithm: DBSCAN: a density-based clustering algorithm.

Input:

D: a data set containing n objects,

ε: the radius parameter, and

MinPts: the neighborhood density threshold.

Output: A set of density-based clusters. 

Method:

        mark all objects as unvisited; 

        do

        randomly select an unvisited object p;

        mark p as visited;

        if the ε-neighborhood of p has at least MinPts objects

          create a new cluster C, and add p to C;

          let N be the set of objects in the ε-neighborhood of p; 

          for each point p′ in N

          if p′ is unvisited

            mark p′ as visited;

            if the ε-neighborhood of p′ has at least MinPts points,

            add those points to N ;

          if p′ is not yet a member of any cluster, add p′ to C;

          end for

          output C;

          else mark p as noise;

        until no object is unvisited
