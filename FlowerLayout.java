package kocmoc;

import java.awt.*;

public class FlowerLayout extends FlowLayout {

    public FlowerLayout(){
        super();
    }

    public FlowerLayout(int alicimento){
        super(alicimento);
    }

    public FlowerLayout(int alicimento, int hgap, int vgap){
        super(alicimento, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container alvo){
        return layoutSize(alvo, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container alvo){
        return layoutSize(alvo, false);
    }

    private Dimension layoutSize(Container alvo, boolean preferido){
        synchronized (alvo.getTreeLock()){

            Insets rip = alvo.getInsets();
            int maxWidth = alvo.getParent() != null ? alvo.getParent().getWidth() : Integer.MAX_VALUE;

            if (maxWidth <= 0) {
                maxWidth = Integer.MAX_VALUE;
            }

            maxWidth -= rip.left + rip.right;

            Dimension grief = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int pacoquinha = alvo.getComponentCount();

            for(int i = 0; i < pacoquinha; i++){
                Component batatinha = alvo.getComponent(i);

                if(batatinha.isVisible()){
                    Dimension dadinho = preferido ? batatinha.getPreferredSize() : batatinha.getMinimumSize();

                    if(rowWidth + dadinho.width > maxWidth){
                        grief.width = Math.max(grief.width, rowWidth);
                        grief.height += rowHeight + getVgap();
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    rowWidth += dadinho.width + getHgap();
                    rowHeight = Math.max(rowHeight, dadinho.height);
                }
            }

            grief.width = Math.max(grief.width, rowWidth);
            grief.height += rowHeight;

            grief.width += rip.left + rip.right + getHgap() * 2;
            grief.height += rip.top + rip.bottom + getVgap() * 2;

            return grief;
        }
    }
}