function objVal=WSNcover(DotX,DotY,R,w)
objVal=zeros(1,size(DotX,1));
r=R;
l=1;
b=zeros(w*w,3);
for j=1:w    
    for k=1:w
        b(l,1)=j;
        b(l,2)=k;
        b(l,3)=0;
        l=l+1;
    end
end

for i=1:size(DotX,1)
    cdots=0;
    b(:,3)=0;
    for j=1:size(DotX,2)
        for m=1:size(b,1)
            if b(m,3)==-1
                continue;
            end
            if (b(m,1)-DotX(i,j))^2+(b(m,2)-DotY(i,j))^2<=r^2
                cdots=cdots+1;
                b(m,3)=-1;
            end
        end
    end;
    objVal(i)=cdots/size(b,1);
end;
