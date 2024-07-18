function [Alpha_score,Alpha_posX,Alpha_posY,Alpha_posZ,Convergence_curve]=GWOD(FoodNumber,Max_iteration,dim,objfun,R,w)
data = 0.4;
lb=ones(1,dim)*(0);
ub=ones(1,dim)*w;

Alpha_posX=zeros(1,dim);
Alpha_posY=zeros(1,dim);
Alpha_posZ=zeros(1,dim);
Alpha_score=-inf;

Beta_posX=zeros(1,dim); 
Beta_posY=zeros(1,dim);
Beta_posZ=zeros(1,dim);
Beta_score=-inf;

Delta_posX=zeros(1,dim);
Delta_posY=zeros(1,dim);
Delta_posZ=zeros(1,dim);
Delta_score=-inf;

Range = repmat((ub-lb),[FoodNumber 1]);
Lower = repmat(lb, [FoodNumber 1]);
PositionsX = rand(FoodNumber,dim) .* Range + Lower;
PositionsY = rand(FoodNumber,dim) .* Range + Lower;
PositionsZ = rand(FoodNumber,dim) .* Range + Lower;

l=0;

while l<Max_iteration
    for i=1:FoodNumber
       flag=1;
        Flag4ub=PositionsX(i,:)>ub;
        Flag4lb=PositionsX(i,:)<lb;
        PositionsX(i,:)=(PositionsX(i,:).*(~(Flag4ub+Flag4lb)))+ub.*Flag4ub+lb.*Flag4lb; % ~表示取反           
 
        Flag5ub=PositionsY(i,:)>ub;
        Flag5lb=PositionsY(i,:)<lb;
        PositionsY(i,:)=(PositionsY(i,:).*(~(Flag5ub+Flag5lb)))+ub.*Flag5ub+lb.*Flag5lb;
        
        Flag5ub=PositionsZ(i,:)>ub;
        Flag5lb=PositionsZ(i,:)<lb;
        PositionsZ(i,:)=(PositionsZ(i,:).*(~(Flag5ub+Flag5lb)))+ub.*Flag5ub+lb.*Flag5lb;

       if(flag==1)
            fitnessw=feval(objfun,PositionsX(i,:),PositionsY(i,:),PositionsZ(i,:),w,R,data);
            fitness=fitnessw;

            if fitness>Alpha_score
                Alpha_score=fitness;
                Alpha_posX=PositionsX(i,:);
                Alpha_posY=PositionsY(i,:);
                Alpha_posZ=PositionsZ(i,:);
            end

            if fitness<Alpha_score && fitness>Beta_score
                Beta_score=fitness;
                Beta_posX=PositionsX(i,:);
                Beta_posY=PositionsY(i,:);
                Beta_posZ=PositionsZ(i,:);
            end

            if fitness<Alpha_score && fitness<Beta_score && fitness>Delta_score
                Delta_score=fitness;
                Delta_posX=PositionsX(i,:);
                Delta_posY=PositionsY(i,:);
                Delta_posZ=PositionsZ(i,:);
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
            
            r5=rand();
            r6=rand();           
            E1=2*a*r5-a;
            F1=2*r6;
            
            D_alphaX=abs(C1*Alpha_posX(j)-PositionsX(i,j));
            X1=Alpha_posX(j)-A1*D_alphaX;         
            D_alphaY=abs(D1*Alpha_posY(j)-PositionsY(i,j));
            Y1=Alpha_posY(j)-B1*D_alphaY;
            D_alphaZ=abs(F1*Alpha_posZ(j)-PositionsZ(i,j));
            Z1=Alpha_posZ(j)-E1*D_alphaZ;
                       
            r1=rand();
            r2=rand();           
            A2=2*a*r1-a;
            C2=2*r2;
            
            r3=rand();
            r4=rand();           
            B2=2*a*r3-a;
            D2=2*r4;
            
            r5=rand();
            r6=rand();           
            E2=2*a*r5-a;
            F2=2*r6;
            
            D_betaX=abs(C2*Beta_posX(j)-PositionsX(i,j));
            X2=Beta_posX(j)-A2*D_betaX;         
            D_betaY=abs(D2*Beta_posY(j)-PositionsY(i,j));
            Y2=Beta_posY(j)-B2*D_betaY;
            D_betaZ=abs(F2*Beta_posZ(j)-PositionsZ(i,j));
            Z2=Beta_posZ(j)-E2*D_betaZ;
            
            r1=rand();
            r2=rand();           
            A3=2*a*r1-a;
            C3=2*r2;
            
            r3=rand();
            r4=rand();           
            B3=2*a*r3-a;
            D3=2*r4;
            
            r5=rand();
            r6=rand();           
            E3=2*a*r5-a;
            F3=2*r6;
            
            D_deltaX=abs(C3*Delta_posX(j)-PositionsX(i,j));
            X3=Delta_posX(j)-A3*D_deltaX;
            D_deltaY=abs(D3*Delta_posY(j)-PositionsY(i,j)); 
            Y3=Delta_posY(j)-B3*D_deltaY; 
            D_deltaZ=abs(F3*Delta_posZ(j)-PositionsZ(i,j)); 
            Z3=Delta_posZ(j)-E3*D_deltaZ; 
            
            PositionsX(i,j)=(X1+X2+X3)/3;
            PositionsY(i,j)=(Y1+Y2+Y3)/3;
            PositionsZ(i,j)=(Z1+Z2+Z3)/3;
        end
    end
    l=l+1;    
    Convergence_curve(l)=Alpha_score;
end