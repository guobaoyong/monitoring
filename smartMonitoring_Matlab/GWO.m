function [Alpha_score,Alpha_posX,Alpha_posY,Convergence_curve]=GWO(FoodNumber,Max_iteration,dim,objfun,R,w)
lb=ones(1,dim)*(0);
ub=ones(1,dim)*w;

Alpha_posX=zeros(1,dim);
Alpha_posY=zeros(1,dim);
Alpha_score=-inf;

Beta_posX=zeros(1,dim);
Beta_posY=zeros(1,dim);
Beta_score=-inf;

Delta_posX=zeros(1,dim);
Delta_posY=zeros(1,dim);
Delta_score=-inf;

Range = repmat((ub-lb),[FoodNumber 1]);
Lower = repmat(lb, [FoodNumber 1]);
PositionsX = rand(FoodNumber,dim) .* Range + Lower;
PositionsY = rand(FoodNumber,dim) .* Range + Lower;

l=0;

while l<Max_iteration
    for i=1:FoodNumber
       flag=1;
        Flag4ub=PositionsX(i,:)>ub;
        Flag4lb=PositionsX(i,:)<lb;
        PositionsX(i,:)=(PositionsX(i,:).*(~(Flag4ub+Flag4lb)))+ub.*Flag4ub+lb.*Flag4lb;         
 
        Flag5ub=PositionsY(i,:)>ub;
        Flag5lb=PositionsY(i,:)<lb;
        
        PositionsY(i,:)=(PositionsY(i,:).*(~(Flag5ub+Flag5lb)))+ub.*Flag5ub+lb.*Flag5lb;

       if(flag==1)
            fitnessw=feval(objfun,PositionsX(i,:),PositionsY(i,:),R,w);
            fitness=fitnessw;

            if fitness>Alpha_score
                Alpha_score=fitness;
                Alpha_posX=PositionsX(i,:);
                Alpha_posY=PositionsY(i,:);
            end

            if fitness<Alpha_score && fitness>Beta_score
                Beta_score=fitness;
                Beta_posX=PositionsX(i,:);
                Beta_posY=PositionsY(i,:);
            end

            if fitness<Alpha_score && fitness<Beta_score && fitness>Delta_score
                Delta_score=fitness;
                Delta_posX=PositionsX(i,:);
                Delta_posY=PositionsY(i,:);
            end
       end

    end
    
    a=2-l*((2)/Max_iteration);
    
    for i=1:FoodNumber
        for j=1:dim
                     
            r1=rand();
            r2=rand();           
            A1=2*a*r1-a;
            C1=2*r2;
            
            r3=rand();
            r4=rand();           
            B1=2*a*r3-a;
            D1=2*r4;

            D_alphaX=abs(C1*Alpha_posX(j)-PositionsX(i,j));
            X1=Alpha_posX(j)-A1*D_alphaX;     
            D_alphaY=abs(D1*Alpha_posY(j)-PositionsY(i,j));
            Y1=Alpha_posY(j)-B1*D_alphaY;
                       
            r1=rand();
            r2=rand();           
            A2=2*a*r1-a;
            C2=2*r2;
            
            r3=rand();
            r4=rand();           
            B2=2*a*r3-a;
            D2=2*r4;

            D_betaX=abs(C2*Beta_posX(j)-PositionsX(i,j));
            X2=Beta_posX(j)-A2*D_betaX;          
            D_betaY=abs(D2*Beta_posY(j)-PositionsY(i,j));
            Y2=Beta_posY(j)-B2*D_betaY;
            
            r1=rand();
            r2=rand();           
            A3=2*a*r1-a;
            C3=2*r2;
            
            r3=rand();
            r4=rand();           
            B3=2*a*r3-a;
            D3=2*r4;
            
            D_deltaX=abs(C3*Delta_posX(j)-PositionsX(i,j));
            X3=Delta_posX(j)-A3*D_deltaX;
            D_deltaY=abs(D3*Delta_posY(j)-PositionsY(i,j)); 
            Y3=Delta_posY(j)-B3*D_deltaY; 

            PositionsX(i,j)=(X1+X2+X3)/3;
            PositionsY(i,j)=(Y1+Y2+Y3)/3;
            
        end
    end
    l=l+1;    
    Convergence_curve(l)=Alpha_score;
end